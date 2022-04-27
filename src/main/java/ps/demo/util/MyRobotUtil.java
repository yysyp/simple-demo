package ps.demo.util;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MyRobotUtil {

    public static long defaultDelay = 50;
    public static Robot robot = null;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void delay() {
        robot.delay((int) defaultDelay);
    }

    public static void ctrlcCtrlv(String str) {
        StringSelection stringSelection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        try {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            delay();
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
                delay();
                robot.keyRelease(KeyEvent.VK_ENTER);
            case '\t':
                robot.keyPress(KeyEvent.VK_TAB);
                delay();
                robot.keyRelease(KeyEvent.VK_TAB);
                return;
        }
    }


    public static void click() {
        click(InputEvent.BUTTON1_MASK);
    }

    public static void click(int button) {
        robot.mousePress(button);
        delay();
        robot.mouseRelease(button);
    }

    public static void click(Point p, double scalingRatio) {
        click(p, scalingRatio, InputEvent.BUTTON1_MASK);
    }

    public static void click(Point p, double scalingRatio, int button) {
        moveTo(p, scalingRatio);
        delay();
        click(button);
    }

    public static void doubleClick() {
        click();
        delay();
        delay();
        delay();
        delay();
        delay();
        click();
    }

    public static void rightClick() {
        click(InputEvent.BUTTON3_MASK);
    }

    public static void rightClick(Point p, double scalingRatio) {
        click(p, scalingRatio, InputEvent.BUTTON3_MASK);
    }

    public static void wheel(int wheelAmt) {
        delay();
        robot.mouseWheel(wheelAmt);
    }

    public static void moveTo(Point p, double scalingRatio) {
        delay();
        robot.mouseMove(-1, -1);
        delay();
        robot.mouseMove((int) (p.x / scalingRatio), (int) (p.y / scalingRatio));
    }

}
