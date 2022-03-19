package ps.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MyFileUtilTest {

    public static void main(String[] args) {
        String folder = "D:\\patrick\\doc3\\3-learn\\33-coding";
        String suffix = "java";
        //String regex = "\"\\s*[^\u0000-\u007F]+\\s*\""; //Chinese?
        String regex = "random";

        Collection<File> listFiles = FileUtils.listFiles(new File(folder), FileFilterUtils.suffixFileFilter(suffix), DirectoryFileFilter.INSTANCE);
        for (File file : listFiles) {
            List<String> finds = MyFileUtil.searchInFile(file, regex);
            if (!finds.isEmpty()) {
                System.out.println("File:" + file + "\t" + finds);
            }
        }

    }


//    public static void loopDir(File file) {
//        if (file.isDirectory()) {
//            for (File f : file.listFiles()) {
//                loopDir(f);
//            }
//        } else if (file.isFile()) {
//            searchInFile(file);
//        }
//    }
//
//    public static void searchInFile(File file) {
//
//    }
}