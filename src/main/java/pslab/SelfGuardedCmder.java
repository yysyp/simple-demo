package pslab;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

public class SelfGuardedCmder {
    private static String startBat = "SelfGuardedCmder.bat";
    private static String workLockName = ".sfgc-lock-wk";
    private static String backupLockName = ".sfgc-lock-bk";
    private static String toExitIndicator = ".sfgc-lock.exit";
    private static File logFile = new File("SelfGuardedCmder.log");
    private static boolean logFlag = false;

    public static void main(String[] args) throws IOException {
        log(getProcessId() + "> -------------------------------------------");
        StringBuffer selfStartCmd = new StringBuffer("java pslab.SelfGuardedCmder ");

        if (args.length < 2) {
            StringBuffer usage = new StringBuffer("#Usage:");
            usage.append(selfStartCmd.toString());
            usage.append("checkIntervalSeconds ");
            usage.append("command ");
            usage.append("[logFlag]");
            usage.append(System.lineSeparator());
            usage.append("i.e. ");
            usage.append(selfStartCmd.toString());
            usage.append("3 \"cmd /c echo %time%>>echo-per3s.log\" 1");
            System.out.println(getProcessId() + "> " + usage);
            System.exit(0);
        }

        int interval = Integer.parseInt(args[0]) * 1000;
        String cmd = args[1];

        if (args.length > 2 && !args[2].trim().equals("")) {
            logFlag = true;
        }

        selfStartCmd.append(args[0]);
        selfStartCmd.append(" ");
        selfStartCmd.append("\"");
        selfStartCmd.append(cmd);
        selfStartCmd.append("\"");
        if (logFlag) {
            selfStartCmd.append(" 1");
        }

        File startBatFile = new File(startBat);
        String startBatCmd = "";
        if (!startBatFile.exists()) {
            startBatFile.createNewFile();
            writeFileContent(startBatFile, selfStartCmd.toString(), "UTF-8");
        }
        startBatCmd = "cmd /c \"" + startBatFile.getAbsolutePath() + "\"";
        log(getProcessId() + "> Start command is:" + selfStartCmd);
        log(getProcessId() + "> And startBatCmd is:" + startBatCmd);
        File workLockFile = new File(workLockName);
        Guard workGuard = new Guard(workLockFile);
        Guard backupGuard = new Guard(new File(backupLockName));
        log(getProcessId() + "> Begin looping, lock=" + workLockFile.getAbsolutePath());
        while (true) {
            try {
                if (checkToExit()) {
                    log(getProcessId() + "> To exit.");
                    if (workGuard.lockedByMe()) {
                        workGuard.unlock();
                    }
                    if (backupGuard.lockedByMe()) {
                        backupGuard.unlock();
                    }
                    System.exit(0);
                }
                if (workGuard.lockedByMe()) {
                    log(getProcessId() + "> Run cmd:[" + cmd + "]");
                    if (backupGuard.lockedByMe()) {
                        backupGuard.unlock();
                        Thread.sleep(interval);
                    }
                    exec(cmd);
                    //check whether backup process is still alive
                    if (backupGuard.tryLock()) {
                        backupGuard.unlock();
                        //start backup process...
                        log(getProcessId() + "> Backup down, starting backup with cmd: [" + startBatCmd + "]");
                        exec(startBatCmd);
                    }
                } else {
                    if (workGuard.tryLock()) {
                        log(getProcessId() + "> Got work lock");
                        //start backup process...
                        log(getProcessId() + "> Starting backup with cmd: [" + startBatCmd + "]");
                        exec(startBatCmd);
                    } else if (!backupGuard.lockedByMe()) {
                        log(getProcessId() + "> Trying backup lock.");
                        backupGuard.tryLock();
                    }
                }
            } catch (Exception e) {
                log(e);
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }
        }

    }


    private static boolean checkToExit() {
        File toExitFile = new File(toExitIndicator);
        return toExitFile.exists();
    }

    private static String stackTraceToString(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    public static void log(Throwable e) {
        log(stackTraceToString(e));
    }

    public static void log(String str) {
        if (!logFlag) {
            return;
        }
        try {
            System.out.println(str);
            str += System.lineSeparator();
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(logFile, true));
            // Writing on output stream
            out.write(str);
            // Closing the connection
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFileContent(File file, String content, String encoding) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            bw.write(content);
        } catch (IOException e) {
            //log(e);
            log(e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private static String getProcessId() {
        String processName = java.lang.management.ManagementFactory
                .getRuntimeMXBean().getName();
        return processName.substring(0, processName.indexOf('@'));
    }


    private static boolean exec(String cmd) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(cmd);
        handleStream(process.getInputStream());
        handleStream(process.getErrorStream());
        return process.waitFor(1, TimeUnit.SECONDS);
    }

    private static void handleStream(InputStream in) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(in))) {
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        log(line);
                    }
                } catch (IOException e) {
                    log(e);
                }
            }
        }).start();
    }

    static class Guard {

        private File file;
        private RandomAccessFile randomAccessFile = null;
        private FileChannel fileChannel;
        private FileLock fileLock;

        public Guard(File file) {
            this.file = file;
        }

        public boolean tryLock() {
            try {
                if (!this.file.exists()) {
                    this.file.createNewFile();
                }
                randomAccessFile = new RandomAccessFile(file, "rw");
                fileChannel = randomAccessFile.getChannel();
                fileLock = fileChannel.tryLock();
                return fileLock != null;
            } catch (IOException e) {
                log(e);
                return false;
            }

        }

        public boolean lockedByMe() {
            return fileLock != null;
        }

        public boolean unlock() {
            if (!lockedByMe()) {
                return false;
            }
            try {
                fileLock.release();
                fileLock = null;
                fileChannel.close();
                randomAccessFile.close();
                file.delete();
                return true;
            } catch (IOException e) {
                log(e);
                return false;
            }
        }


    }

}
