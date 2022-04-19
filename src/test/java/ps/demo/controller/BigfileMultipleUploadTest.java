package ps.demo.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;
import ps.demo.util.MyRestTemplateUtil;
import ps.demo.util.MyStringUtil;

import java.io.File;
import java.nio.charset.Charset;

public class BigfileMultipleUploadTest {

    public static void main(String[] args) {
        File file = MyFileUtil.getFileTsInHomeDir("bigfile-500m.txt");
        for (int i = 0; i < 500; i++) {
            String str = MyStringUtil.randomAlphabetic(1024*1024);
            MyReadWriteUtil.writeObjectToFile(file, str, Charset.forName("UTF-8"), true);
        }
        System.out.println("file created!");


        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("fileName", "bigfile-500m.txt");

        String url = "http://localhost:8080/api/upload/file";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String result = MyRestTemplateUtil.getInstance().submitFormForString(url, headers, param);
        System.out.println("result="+result);

    }
}
