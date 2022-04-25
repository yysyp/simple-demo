package ps.demo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MyImageUtil {

    public static BufferedImage screenShot() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w = (int) screenSize.getWidth();
        int h = (int) screenSize.getHeight();
        return screenShot(0, 0, w, h);
    }

    public static BufferedImage screenShot(int x, int y, int width, int height) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        BufferedImage screenImg = robot.createScreenCapture(new Rectangle(x, y,
                width, height));
        return screenImg;
    }

    public static void screenShot(File file) {
        BufferedImage bufferedImage = screenShot();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ImageIO.write(bufferedImage, "png", fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage loadImage(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return ImageIO.read(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
