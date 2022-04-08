package ps.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ps.demo.service.MyFileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by yunpeng.song on 5/15/2020.
 */


@Slf4j
@RestController
@RequestMapping("/api/download")
public class MyDownloadController {

    @Autowired
    MyFileService fileService;

    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * The key should be the base64 absolute path;
     * @param key
     * @param request
     * @param response
     * @return
     */
    //String key = new String(encoder.encode("C:\\Users\\yunpeng.song\\test.txt".getBytes()), "UTF-8")
    @GetMapping("/file/key")
    @ResponseBody
    public String downloadFileByKey(@RequestParam(value = "key", required = false) String key, HttpServletRequest request, HttpServletResponse response) {
        try {
            String filePath = new String(decoder.decode(key), "UTF-8");
            File file = new File(filePath);
            String displayFileName = file.getName();
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            //Clean response
            response.reset();
            //Set response Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(displayFileName, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            return "Success";
        } catch (Exception e) {
            log.error("-------->>" + e.getMessage(), e);
            return "Failed";
        }
    }

    /**
     * path will be the relative path to upload-folder.
     * ex: 2022-04-07/example.jpg
     * @param path
     * @param request
     * @return
     */
    @GetMapping("/file/path")
    public ResponseEntity<Resource> downloadFileByPath(
            @RequestParam(value="path", required = true) String path
            , HttpServletRequest request) {
        return getResourceByFile(path, request);
    }

    /**
     * fileName: the relative filename to upload-folder.
     * ex: abc.txt
     * @param fileName
     * @param request
     * @return
     */
    @GetMapping("/file/name/{fileName:.+}")
    public ResponseEntity<Resource> downloadFileByName(@PathVariable String fileName, HttpServletRequest request) {
        return getResourceByFile(fileName, request);
    }

    private ResponseEntity<Resource> getResourceByFile(String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileService.loadFileAsResource(fileName);

        //InputStream inputStream = inputStream = new FileInputStream(fileName);
        //Resource springResource = new InputStreamResource(inputStream);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("--------->>>Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
