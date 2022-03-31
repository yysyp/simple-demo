package ps.demo.util;

public class MyCmdRunTest {

    public static void main(String[] args) throws InterruptedException {
        //new MyCmdRun("ping baidu.com").run();
        //MyCmdRun myCmdRun = new MyCmdRun("ping -t baidu.com");
        ///bin/sh -c xxx
        MyCmdRun myCmdRun = new MyCmdRun("cmd /c for /l %i in (1,1,1000) do (echo this is line%i)");
        Thread thread = new Thread(myCmdRun);
        thread.start();
        Thread.sleep(10000);
        myCmdRun.destroy();
        System.out.println("CmdRunTest ENDED..."+myCmdRun.getExitCode());
        //System.out.println("--->"+myCmdRun.getOutput());
        System.out.println(myCmdRun.getOutputString());
    }
}