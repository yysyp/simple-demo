package ps.demo.util;

import lombok.extern.slf4j.Slf4j;
import ps.demo.dto.AddrInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MyYamlUtilTest {

    public static void main(String[] args) {
        File file = MyFileUtil.getFileInHomeDir("2022-06-02_171841-test.yaml");

        Map map = MyYamlUtil.readObject(new File("src/main/resources/application.yml"), HashMap.class);
        log.info("--map={}", map);

        List<AddrInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AddrInfo addrInfo = AddrInfo.builder().country("aaa" + i)
                    .province("bbb" + i).build();
            list.add(addrInfo);
        }
        MyYamlUtil.writeObject(file, list);

        List<AddrInfo> list1 = MyYamlUtil.readListOfObjects(file, AddrInfo.class);
        log.info("list1 = {}", list1);

    }

}