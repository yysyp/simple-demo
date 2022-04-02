package ps.demo.controller;

import com.alibaba.excel.util.StringUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.dto.request.UploadHi;
import ps.demo.dto.response.HealthzResponse;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.exception.ServerApiException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunpeng.song on 5/15/2020.
 */
@Tag(name = "This is a file upload controller")
@Slf4j
@RestController
@RequestMapping("/api/upload")
public class MyUploadController {

    @PostMapping(value = "/hi", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String hi(@RequestBody UploadHi uploadHi) {
        log.info("--->> uploadHi.index={}, num={}", uploadHi.getIndex(), uploadHi.getNum());
        int size = uploadHi.getFileStr().getBytes(StandardCharsets.UTF_8).length;
        List<Object> multipleStr = new ArrayList<>();
        for (int i = 0; i < uploadHi.getNum(); i++) {
            multipleStr.add(new byte[size]);
        }
        return LocalDateTime.now().toString() + uploadHi.getLength();
    }

    @PostMapping(value = "/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "key", required = false) String key,
                                   HttpServletRequest req) {
        if (file == null) {
            throw new BadRequestException(CodeEnum.BAD_REQUEST, false, "File is required.");
        }
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(key)) {
                key = fileName;
            }

            String uploadFolder = System.getProperty("user.dir") + "/upload-folder/";
            String destFileName = uploadFolder + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")) + fileName;

            File destFile = Paths.get(destFileName).toFile();
            log.info("--------->>File uploaded to file={}", destFile);
            destFile.getParentFile().mkdirs();
            file.transferTo(destFile);

        } catch (Exception e) {
            throw new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, true, "--------->>File upload failed, ex:" + e.getMessage());
        }
        return "SUCCESS";
    }

//    @GetMapping
//    public ModelAndView navigateToUploadPage(Model model) {
//        model.addAttribute("title", "Upload File.");
//        return new ModelAndView("upload/fileupload", "model", model);
//    }
//
//    @PostMapping("/file")
//    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(value = "key", required = false) String key,
//                                   HttpServletRequest req, Model model) {
//        if (file == null) {
//            throw new BadRequestException(CodeEnum.BAD_REQUEST, false, "File is required.");
//        }
//        try {
//            String fileName = file.getOriginalFilename();
//            if (StringUtils.isEmpty(key)) {
//                key = fileName;
//            }
//
//            String uploadFolder = System.getProperty("user.dir") + "/upload-folder/";
//            String destFileName = uploadFolder + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator
//                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS")) + fileName;
//
//            File destFile = Paths.get(destFileName).toFile();
//            log.info("--------->>File uploaded to file={}", destFile);
//            destFile.getParentFile().mkdirs();
//            file.transferTo(destFile);
//
//        } catch (Exception e) {
//            throw new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, true, "--------->>File upload failed, ex:" + e.getMessage());
//        }
//        model.addAttribute("title", "Upload Success!");
//        return new ModelAndView("upload/uploadsuccess", "model", model);
//    }

}
