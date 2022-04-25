package pslab;

import ps.demo.pics.s3.MyFindSubImageUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyImageUtil;
import ps.demo.util.MyRobotUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;

public class AutoExample {

    public static void main(String [] args) throws Exception {
        double similarity = 0.85d;
        File winFile = MyFileUtil.getFileInHomeDir("win.png");
        Point cp = MyFindSubImageUtil.findSubImageCenter(MyImageUtil.screenShot(), MyImageUtil.loadImage(winFile), similarity);
        MyRobotUtil.click(cp);

    }

}
