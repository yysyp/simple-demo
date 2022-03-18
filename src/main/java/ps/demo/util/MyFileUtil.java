package ps.demo.util;

import org.apache.catalina.core.ApplicationPart;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class MyFileUtil {

    public static String getUserHomeDir() {
        return Paths.get(System.getProperty("user.home")).toAbsolutePath()
                .toString();
    }

    public static String getCurrentDir() {
        return Paths.get(System.getProperty("user.dir")).toAbsolutePath()
                .toString();
    }

    public static File getFileInHomeDir(String fileName) {
        return new File(getUserHomeDir() + "/" + fileName);
    }

    public static File getLogInHomeDir(String key) {
        return new File(getUserHomeDir() + "/" + key + "-" + MyTimeUtil.getNowStr() + ".log");
    }

    public static void setFullPermission(File file) {
        if (file != null) {
            file.setReadable(true, false);
            file.setExecutable(true, false);
            file.setWritable(true, false);
        }
    }

    public static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
        } else {
            file.delete();
        }
    }

    public static File resolverTempFile(MultipartFile multipartFile) throws IOException {
        Class<? extends MultipartFile> multipartFileClass = multipartFile.getClass();
        try {
            Field partField = multipartFileClass.getDeclaredField("part");
            partField.setAccessible(true);
            ApplicationPart part = (ApplicationPart) partField.get(multipartFile);
            Field fileItemField = ApplicationPart.class.getDeclaredField("fileItem");
            fileItemField.setAccessible(true);
            FileItem fileItem = (FileItem) fileItemField.get(part);
            Class<? extends FileItem> fileItemClass = fileItem.getClass();
            Field tempFileField = fileItemClass.getDeclaredField("tempFile");
            tempFileField.setAccessible(true);
            return (File) tempFileField.get(fileItem);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建文件支持多级目录
     *
     * @param file 需要创建的文件
     * @return 是否成功, 如果存在则返回成功
     */
    public final static boolean createFiles(File file) {
        if (file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            if (!file.exists()) {
                return file.mkdirs();
            }
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    try {
                        return file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 创建文件支持多级目录
     *
     * @param file 需要创建的文件
     * @return 是否成功, 如果存在则返回成功
     * @para isReNew 存在的时候是否重新创建
     */
    public final static boolean createFiles(File file, boolean isReNew) {
        if (file.exists()) {
            if (isReNew) {
                if (file.delete()) {
                    try {
                        return file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        if (file.isDirectory()) {
            if (!file.exists()) {
                return file.mkdirs();
            }
        } else {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    try {
                        return file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 创建文件支持多级目录
     *
     * @param path 需要创建的文件
     * @return 是否成功
     */
    public static boolean createFile(String path) {
        if (path != null && path.length() > 0) {
            try {
                File file = new File(path);
                if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
                    return file.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 获取文件后缀名
     *
     * @param file file
     * @return file's suffix
     */

    public static String suffix(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.indexOf(".") + 1);
    }

    /**
     * 获取文件的hash
     *
     * @param file     file
     * @param HashTyle MD5,SHA-1,SHA-256
     * @return
     */
    public static String fileHash(File file, String HashTyle) {
        try (InputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance(HashTyle);
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] md5Bytes = md.digest();
            StringBuilder hexValue = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
