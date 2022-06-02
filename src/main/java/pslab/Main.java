package pslab;

import lombok.extern.slf4j.Slf4j;
import ps.demo.dto.AddrInfo;
import ps.demo.util.MyArgsUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyYamlUtil;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the jar main entry.
 * Run mvn to build: mvn clean package -D spring-boot.repackage.skip=true -D fat.jar=true
 * java -jar target/simple-demo-1.0.0-jar-with-dependencies.jar
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, List<String>> argsMap = MyArgsUtil.argsToMap(args);
        if (argsMap.containsKey("--help") || argsMap.containsKey("-h")) {
            printUsage();
            return;
        }

    }

    public static void printUsage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The usage:"+System.lineSeparator());

        System.out.println(stringBuilder);
    }

}
