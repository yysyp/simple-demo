package ps.demo.pics.s4;


import java.awt.*;

public class BsearchImage {

//    // look for all (x,y) positions where target appears in desktop
//    List<Loc> findMatches(Image desktop, Image target, float threshold) {
//        List<Loc> locs;
//        for (int y=0; y<desktop.height()-target.height(); y++) {
//            for (int x=0; x<desktop.width()-target.width(); x++) {
//                if (imageDistance(desktop, x, y, target) < threshold) {
//                    locs.append(Loc(x,y));
//                }
//            }
//        }
//        return locs;
//    }
//
//    // computes the root mean squared error between a rectangular window in
//// bigImg and target.
//    float imageDistance(Image bigImg, int bx, int by, Image target) {
//        float sum_dist2 = 0.0;
//        for (int y=0; y<target.height(); y++) {
//            for (int x=0; x<target.width(); x++) {
//                // assume RGB images...
//                for (int colorChannel=0; colorChannel<3; colorChannel++) {
//                    float dist = target.getPixel(x,y) - bigImg.getPixel(bx+x,by+y);
//                    sum_dist2 += dist * dist;
//                }
//            }
//        }
//        return Math.sqrt(sum_dist2 / target.width() / target.height());
//    }
}
