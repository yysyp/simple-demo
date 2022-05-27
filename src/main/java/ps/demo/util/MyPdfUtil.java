package ps.demo.util;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.util.List;

public class MyPdfUtil {


    public static void mulFilesToOne(List<File> files, File targetPath) throws Exception {
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        for (File f : files) {
            if (f.exists() && f.isFile()) {
                mergePdf.addSource(f);
            }
        }
        mergePdf.setDestinationFileName(targetPath.getPath());
        mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

}
