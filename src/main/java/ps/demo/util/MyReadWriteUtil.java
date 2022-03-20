package ps.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MyReadWriteUtil {

    public static void writeToFileInHomeDir(File file, Object content) {
        writeToFileInHomeDir(file, content, StandardCharsets.UTF_8, true);
    }

    public static void writeToFileInHomeDir(File file, Object content, Charset charsetName, boolean append) {
        try {
            String lineEnding = System.lineSeparator();
            FileUtils.writeStringToFile(file, MyJsonUtil.object2Json(content) + lineEnding, charsetName, append);
        } catch (IOException e) {
            throw new RuntimeException("Write to File failed!", e);
        }
    }

    public static <T> T readObjectFromFileInHomeDir(File file, Class<T> c) {
        try {
            return MyJsonUtil.json2Object(FileUtils.readFileToString(file, StandardCharsets.UTF_8), c);
        } catch (IOException e) {
            throw new RuntimeException("Read from File failed!", e);
        }
    }

    public static <T> void writeObjectsToFileInHomeDir(File file, List<T> lists) {
        writeObjectsToFileInHomeDir(file, lists, true);
    }

    public static <T> void writeObjectsToFileInHomeDir(File file, List<T> lists, boolean append) {
        try {
            List<String> stringList = MyJsonUtil.listObject2ListJson(lists);
            FileUtils.writeLines(file, stringList, append);
        } catch (IOException e) {
            throw new RuntimeException("Write to File failed!", e);
        }
    }

    public static <T> List<T> readObjectsFromFileInHomeDir(File file, Class<T> c) {
        return readObjectsFromFileInHomeDir(file, StandardCharsets.UTF_8, c);
    }

    public static <T> List<T> readObjectsFromFileInHomeDir(File file, Charset charset, Class<T> c) {
        try {
            List<String> lines = FileUtils.readLines(file, charset);
            return lines.stream().map(s -> MyJsonUtil.json2Object(s, c)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Read from File failed!", e);
        }
    }


    public final static int countLines(File file) {
        try (LineNumberReader rf = new LineNumberReader(new FileReader(file))) {
            long fileLength = file.length();
            rf.skip(fileLength);
            return rf.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public final static List<String> lines(File file) {
        List<String> list = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static String readFileContent(File file, String encoding) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            String content = "";
            StringBuilder sb = new StringBuilder();
            while ((content = bf.readLine()) != null) {
                sb.append(content);
                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
    public static void writeFileContent(String content, File file) {
        writeFileContent(content, file, "UTF-8");
    }

    public static void writeFileContent(String content, File file, String encoding) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
            bw.write(content);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }




}
