package ps.demo.util;

import ps.demo.util.range.MyRange;
import sun.misc.Unsafe;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class MyImageUtilTest {


    public static void main(String[] args) {
        Rectangle fr = MyImageUtil.getScreenSizeRectangle();
        System.out.println("screen: fr=" + fr);

        Point p = null;
        Rectangle r = null;
        r = MyImageUtil.getLeftScreenSize(fr, 0.5d);
        System.out.println("left screen: r=" + r);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("left-screen.png"), r);

        r = MyImageUtil.getRightScreenSize(fr, 0.25d);
        System.out.println("right screen: r=" + r);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("right-screen.png"), r);
        p = MyImageUtil.convertRightScreenSizePoint(new Point(10, 2), r.width, 0.25d);
        System.out.println("left to full point:" + p);

        r = MyImageUtil.getUpScreenSize(fr, 0.5d);
        System.out.println("up screen: r=" + r);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("up-screen.png"), r);

        r = MyImageUtil.getBottomScreenSize(fr, 0.25d);
        System.out.println("bottom screen: r=" + r);
        p = MyImageUtil.convertBottomScreenSizePoint(new Point(2, 10), r.height, 0.25d);
        System.out.println("botton to full point:" + p);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("bottom-screen.png"), r);

        System.out.println("------------------------------Quadrant--------------------------------");
        r = MyImageUtil.getQuadrant1(fr);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant1.png"), r);
        p = MyImageUtil.convertQuadrant1Point(r, new Point(1, 2));
        System.out.println("Quadrant1 convert point:" + p);
        r = MyImageUtil.getQuadrant2(fr);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant2.png"), r);
        p = MyImageUtil.convertQuadrant2Point(r, new Point(1, 2));
        System.out.println("Quadrant2 convert point:" + p);
        r = MyImageUtil.getQuadrant3(fr);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant3.png"), r);
        p = MyImageUtil.convertQuadrant3Point(r, new Point(1, 2));
        System.out.println("Quadrant3 convert point:" + p);
        r = MyImageUtil.getQuadrant4(fr);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant4.png"), r);
        p = MyImageUtil.convertQuadrant4Point(r, new Point(1, 2));
        System.out.println("Quadrant4 convert point:" + p);


        System.out.println("------------------------------MyRange--------------------------------");

        MyRange q1 = MyRange.getQuadrant1(fr.width, fr.height);
        q1.setX(1);
        q1.setY(2);
        System.out.println("q1 = " + q1);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant1.png"), q1.toRectangleInFullCoordinate());

        MyRange q2 = MyRange.getQuadrant2(fr.width, fr.height);
        q2.setX(1);
        q2.setY(2);
        System.out.println("q2 = " + q2);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant2.png"), q2.toRectangleInFullCoordinate());


        MyRange q3 = MyRange.getQuadrant3(fr.width, fr.height);
        q3.setX(1);
        q3.setY(2);
        System.out.println("q3 = " + q3);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant3.png"), q3.toRectangleInFullCoordinate());


        MyRange q4 = MyRange.getQuadrant4(fr.width, fr.height);
        q4.setX(1);
        q4.setY(2);
        System.out.println("q4 = " + q4);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("Quadrant4.png"), q4.toRectangleInFullCoordinate());

        MyRange x = new MyRange(0.3d, 0.3d, 0.3d, 0.3d);
        System.out.println("x="+x);
        //MyImageUtil.screenShot(MyFileUtil.getFileTsInHomeDir("x.png"), x.toRectangleInFullCoordinate());

        x.setX(10);
        x.setY(20);
        System.out.println("x="+x);

        //BufferedImage bi = MyImageUtil.screenShot(x.toRectangleInFullCoordinate());

    }

}