package ps.demo.pics.s3;


import marvin.image.MarvinImage;
import marvin.image.MarvinSegment;
import marvin.io.MarvinImageIO;
import ps.demo.util.MyImageUtil;

import java.awt.*;

import static marvin.MarvinPluginCollection.*;


public class FindSubimageWindow {

    public FindSubimageWindow(double similarity) {

        //MarvinImage window = MarvinImageIO.loadImage("C:\\Users\\Dell\\ss3.png");
        MarvinImage window = new MarvinImage(MyImageUtil.screenShot());
        MarvinImage eclipse = MarvinImageIO.loadImage("C:\\Users\\Dell\\1.png");
        //MarvinImage progress = MarvinImageIO.loadImage("./res/progress_icon.png");

        MarvinSegment seg1, seg2;
        seg1 = findSubimage(eclipse, window, 0, 0, similarity);
        drawRect(window, seg1.x1, seg1.y1, seg1.x2 - seg1.x1, seg1.y2 - seg1.y1);

        //seg2 = findSubimage(progress, window, 0, 0);
        //drawRect(window, seg2.x1, seg2.y1, seg2.x2-seg2.x1, seg2.y2-seg2.y1);
        //drawRect(window, seg1.x1-10, seg1.y1-10, (seg2.x2-seg1.x1)+25, (seg2.y2-seg1.y1)+20);

        MarvinImageIO.saveImage(window, "C:\\Users\\Dell\\result1.png");
    }

    private void drawRect(MarvinImage image, int x, int y, int width, int height) {
        x -= 4;
        y -= 4;
        width += 8;
        height += 8;
        image.drawRect(x, y, width, height, Color.red);
        image.drawRect(x + 1, y + 1, width - 2, height - 2, Color.red);
        image.drawRect(x + 2, y + 2, width - 4, height - 4, Color.red);
    }

    public static void main(String[] args) {
        double similarity = 0.9d;
        new FindSubimageWindow(similarity);
    }
}
