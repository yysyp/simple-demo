package ps.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MyPdfUtilTest {

    public static void main(String[] args) {
        List<File> files = new ArrayList();
        File folder = new File("D:\\patrick\\book\\book1");
        File[] tempList = folder.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            File file = tempList[i];
            if (file.isFile() && file.getName().toLowerCase().endsWith(".pdf")) {
                files.add(file);
            }
        }
        files.sort(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Long.valueOf(getFileCreationEpoch(o1)).
                        compareTo(Long.valueOf(getFileCreationEpoch(o2)));
            }
        });
        try {
            File mergedFile = new File(folder.getParentFile(), folder.getName() + ".pdf");
            MyPdfUtil.mulFilesToOne(files, mergedFile);
            log.info("--->> PDF merge done!, merged pdf={}", mergedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getFileCreationEpoch(File file) {
        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(),
                    BasicFileAttributes.class);
            return attr.creationTime()
                    .toInstant().toEpochMilli();
        } catch (IOException e) {
            throw new RuntimeException(file.getAbsolutePath(), e);
        }
    }

}