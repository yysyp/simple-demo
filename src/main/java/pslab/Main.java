package pslab;

import lombok.extern.slf4j.Slf4j;
import ps.demo.dto.AddrInfo;
import ps.demo.util.*;

import java.io.File;
import java.util.*;

/**
 * This is the jar main entry.
 * Run mvn to build: mvn clean package -D spring-boot.repackage.skip=true -D fat.jar=true
 * java -jar target/simple-demo-1.0.0-jar-with-dependencies.jar
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> argsMap = MyArgsUtil.argsToMap(args);
        if (argsMap.containsKey("--help") || argsMap.containsKey("-h")) {
            printUsage();
            return;
        }

        Runtime r = Runtime.getRuntime();
        r.addShutdownHook(new Thread() {
            public void run() {
                log.info("shutDown hook");

            }
        });

        if (argsMap.containsKey("-at") && argsMap.containsKey("-c")) {
            atCommand(argsMap);
        }

    }

    private static void atCommand(Map<String, List<String>> argsMap) {
        String command = argsMap.get("-c").get(0);
        MyCmdRun myCmdRun = new MyCmdRun(command);
        String atHhmm = argsMap.get("-at").get(0);
        String beforeHhmm = "05:00";

        Timer timer = new Timer();
        long sleepms = 3 * 60 * 1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String nowHhmm = MyTimeUtil.getNowStr("HH:mm");
                Date atDate = MyTimeUtil.toDate(atHhmm, "HH:mm");
                Date beforeDate = MyTimeUtil.addDays(MyTimeUtil.toDate(beforeHhmm, "HH:mm"), 1);
                Date nowDate = MyTimeUtil.toDate(nowHhmm, "HH:mm");
                if (nowDate.after(atDate) && nowDate.before(beforeDate)) {
                    timer.cancel();
                    myCmdRun.run();
                }
            }
        }, sleepms, sleepms);
    }

    public static void printUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The usage:" + System.lineSeparator());
        stringBuilder.append("'-at HH:mm -c cmd' to execute command at HH:mm. ex: -at 23:00 -c \"shutdown -s -t 60\"");
        System.out.println(stringBuilder);
    }

}
