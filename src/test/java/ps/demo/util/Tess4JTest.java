package ps.demo.util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tess4JTest {

    //Tesseract OCR
    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        //tesseract.setLanguage("eng");
        tesseract.setLanguage("chi_sim");
        tesseract.setOcrEngineMode(1);
        try {
            //Path dataDirectory = Paths.get(ClassLoader.getSystemResource("data").toURI());
            //tesseract.setDatapath(dataDirectory.toString());
            tesseract.setDatapath("D:/application/Tess4J-3.4.8-src/Tess4J/tessdata");
            // the path of your tess data folder
            // inside the extracted file
            String text = tesseract.doOCR(MyFileUtil.getFileInHomeDir("ocrtestimag-chi.jpg"));
            // path of your image file
            System.out.print(text);
//            BufferedImage image = ImageIO.read(Tess4JTest.class.getResourceAsStream("/ocrexample.jpg"));
//            String result = tesseract.doOCR(image);
//            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
