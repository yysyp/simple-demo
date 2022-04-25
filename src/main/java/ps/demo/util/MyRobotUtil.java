package ps.demo.util;

import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MyRobotUtil {

    public static long defaultDelay = 30;
    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void ctrlcCtrlv(String str) {
        StringSelection stringSelection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        try {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void typeEnter() {
        type('\n');
    }

    public static void typeTab() {
        type('\t');
    }

    public static void type(char c) {
        switch (c) {
            case '\n':
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
            case '\t':
                robot.keyPress(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_TAB);
            return;
        }
    }

    public static void click(Point p, double scalingRatio) {
        robot.mouseMove(-1,-1);
        robot.mouseMove((int)(p.x/scalingRatio), (int)(p.y/scalingRatio));
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

}
