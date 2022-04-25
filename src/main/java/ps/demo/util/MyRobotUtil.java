package ps.demo.util;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MyRobotUtil {

    public static long defaultDelay = 30;
    public static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Robot init error: "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static void ctrlcCtrlv(String str) {
        StringSelection stringSelection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        try {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            Thread.sleep(defaultDelay);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void click(Point p) {
        robot.mouseMove(p.x, p.y);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

}
