package pslab;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import ps.demo.util.MyArgsUtil;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;
import ps.demo.util.MyThymeleafUtil;
import ps.demo.util.json.JSONException;
import ps.demo.util.json.JSONObject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class Kuaima {

    private static Properties properties;

    public static void main(String[] args) throws JSONException {
        Map<String, String> argsMap = MyArgsUtil.assemberArgs(args);
        String tfsource = StringUtils.isBlank(argsMap.get("tfsource")) ? "tfsource" : argsMap.get("tfsource").trim();

        properties = MyReadWriteUtil.readProperties(new File(tfsource, "tf.properties"));
        Collection<File> tfs = FileUtils.listFiles(new File(tfsource), new String[]{"tf"}, false);

        String encoding = properties.getProperty("encoding");
        String targetPath = properties.getProperty("tftargetPath");
        File header = new File(tfsource, "header_tf");
        File footer = new File(tfsource, "footer_tf");
        String headerContent = MyReadWriteUtil.readFileContent(header, encoding);
        String footerContent = MyReadWriteUtil.readFileContent(footer, encoding);
        Map propMap = new HashMap(properties);
        String entityJson = properties.getProperty("entityJson");
        JSONObject jsonObject = new JSONObject(entityJson);
        propMap.put("entityJson", jsonObject);
        for (File tf : tfs) {
            String fileName = tf.getName();
            String relativePath = properties.getProperty(fileName);
            if (StringUtils.isBlank(relativePath)) {
                log.info("Kuaima file: {} ignored as no relativePath in tf.properties for it", tf);
                continue;
            }
            try {
                relativePath = MyThymeleafUtil.processText(relativePath, propMap);
                StringBuffer content = new StringBuffer(headerContent);
                content.append(System.lineSeparator());
                content.append(MyReadWriteUtil.readFileContent(tf, encoding));
                content.append(System.lineSeparator());
                content.append(footerContent);
                String result = MyThymeleafUtil.processText(content.toString(), propMap);
                File absPath = new File(targetPath, relativePath);
                MyFileUtil.createFiles(absPath);
                MyReadWriteUtil.writeFileContent(absPath, result, encoding);
            } catch (Exception e) {
                log.error("Kuaima processing err, msg={}", e.getMessage(), e);
            }
        }

        log.info("Kuaima processing Done!");
    }

//    public static JSONObject parseJSON(String jsonStr) {
//        JSONObject jsonObject = JSONObject.fromObject(joStr);
//    }

}
