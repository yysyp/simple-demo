package pslab;

//import ps.demo.util.MyCmdRun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;

public class CkShutd {

    /**
     * * Put pslab/CkShutd to c:/ and run below command:
     * cd /d c:/ && java pslab.CkShutd
     */
    public static void main(String[] args) throws IOException {
        LocalTime time = LocalTime.now();
        LocalTime timeAfter = LocalTime.of(13, 00, 00);
        LocalTime timeBefore = LocalTime.of(5, 0, 0);

        if (time.isAfter(timeAfter) || time.isBefore(timeBefore)) {
            //new MyCmdRun("shutdown -s -t 180").run();
            Process process = Runtime.getRuntime().exec("shutdown -s -t 180");
                    //.exec("cmd /c echo hihi > D:/patrick/haha.txt");

            Thread outThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeOutput(process.getInputStream());
                }
            });
            outThread.start();

            Thread errThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    writeOutput(process.getErrorStream());
                }
            });
            errThread.start();
        }
    }

    private static void writeOutput(InputStream in) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(in))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
