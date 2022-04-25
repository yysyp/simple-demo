package ps.demo.pics.s3;

import marvin.image.MarvinImage;
import marvin.image.MarvinSegment;

import java.awt.*;
import java.awt.image.BufferedImage;

import static marvin.MarvinPluginCollection.findSubimage;

public class MyFindSubImageUtil {

    public static Rectangle findSubImage(BufferedImage bigImage, BufferedImage subImage, double similarity) {
        MarvinImage big = new MarvinImage(bigImage);
        MarvinImage small = new MarvinImage(subImage);
        MarvinSegment seg = findSubimage(small, big, 0, 0, similarity);
        if (seg == null) {
            throw new RuntimeException("Sub image not found");
        }
        return new Rectangle(seg.x1, seg.y1, seg.width, seg.height);
    }

    public static Point findSubImageCenter(BufferedImage bigImage, BufferedImage subImage, double similarity) {
        Rectangle rectangle = findSubImage(bigImage, subImage, similarity);
        Point point = new Point(rectangle.x + (rectangle.width / 2), rectangle.y + (rectangle.height / 2));
        return point;
    }

}
