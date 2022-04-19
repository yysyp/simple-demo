package ps.demo.util;

public class MyStringUtilTest {

    public static void main(String[] args) {
        MyReadWriteUtil.writeObjectToFileTsInHomeDir(MyStringUtil.randomAlphabetic(1024*1024));
    }

}