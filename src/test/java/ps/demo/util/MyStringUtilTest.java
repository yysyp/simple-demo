package ps.demo.util;

import static org.junit.jupiter.api.Assertions.*;

public class MyStringUtilTest {

    public static void main(String[] args) {
        MyReadWriteUtil.writeToFileTsInHomeDir(MyStringUtil.randomAlphabetic(1024*1024));
    }

}