

package ps.demo.qnpoll.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.annotation.MyPermission;
import ps.demo.common.MyBaseController;
import ps.demo.qn.dto.QuestionnaireDto;
import ps.demo.qn.dto.QuestionnaireResultDto;
import ps.demo.qn.service.QuestionnaireResultServiceImpl;
import ps.demo.qn.service.QuestionnaireServiceImpl;
import ps.demo.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/qnpoll")
public class PollController extends MyBaseController {

    @Autowired
    private QuestionnaireServiceImpl questionnaireserviceimpl;

    @Autowired
    private QuestionnaireResultServiceImpl questionnaireResultService;

    @Value("${dir.upload-folder}")
    private String uploadDir;

    @GetMapping(value = "/{uri:[a-zA-Z0-9]+}")
    //@GetMapping(value = "/{uri}")
    public ResponseEntity showQuestionnaire(@PathVariable String uri, HttpServletRequest request,
                                            HttpServletResponse response) {
        //HtmlUtils.htmlEscape(uri);
        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findByAttribute("uri", uri).get(0);
        StringBuffer html = new StringBuffer();
        if (questionnaireDto.getWholeHtml() == null || !questionnaireDto.getWholeHtml()) {
            html.append(HTML_BEFORE);
        }

        File file = new File(uploadDir, questionnaireDto.getHtmlFile());
        html.append(MyReadWriteUtil.readFileContent(file));
        if (questionnaireDto.getWholeHtml() == null || !questionnaireDto.getWholeHtml()) {
            html.append(HTML_AFTER);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        ResponseEntity<String> responseEntity = new ResponseEntity(html, headers, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(value = "/{uri:[a-zA-Z0-9]+}/result")
    public String questionnaireResponse(@PathVariable String uri, HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        if (CollectionUtils.isEmpty(parameters)) {
            return CW_ERROR;
        }
        Map<String, String[]> treeMap = new TreeMap<>(parameters);

        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findByAttribute("uri", uri).get(0);
        String names = questionnaireDto.getFormItemNames();
        if (StringUtils.isNotBlank(names)) {
            String[] a = StringUtils.splitByWholeSeparator(names, ",");
            for (String ai : a) {
                String key = (ai+"").trim();
                if (!treeMap.containsKey(key)) {
                    treeMap.put(key, new String[]{""});
                }
            }
        }

        QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
        questionnaireResultDto.setUri(uri);
        questionnaireResultDto.setResponseData(MyJsonUtil.object2JsonString(treeMap));
        initBaseCreateModifyTs(questionnaireResultDto);
        questionnaireResultService.save(questionnaireResultDto);
        return WC_COMPLETED;
    }

    @MyPermission(roles = {"user", "admin"})
    @GetMapping(value = "/{uri:[a-zA-Z0-9]+}/result-view")
    public ModelAndView viewQuestionnaire(@PathVariable String uri, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        return new ModelAndView("redirect:/api/qn/questionnaire-result");
    }

    @MyPermission(roles = {"user", "admin"})
    @GetMapping(value = "/{uri:[a-zA-Z0-9]+}/result-excel")
    public String downloadQuestionnaire(@PathVariable String uri, HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findByAttribute("uri", uri).get(0);

        Pageable pageable = PageRequest.of(0, 60000);
        QuestionnaireResultDto param = new QuestionnaireResultDto();
        param.setUri(uri);
        List<QuestionnaireResultDto> questionnaireResultDtoList = questionnaireResultService
                .findByAttributesInPage(param, false, pageable).getContent();
        List<String> head = questionnaireResultDtoList.get(0).toKeyList();
        List<List<Object>> data = questionnaireResultDtoList.stream().map(e -> {
            e.setName(questionnaireDto.getName());
            return e.toList();
        }).collect(Collectors.toList());

        //Clean response
        response.reset();
        //Set response Header
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(MyFileUtil.toValidFileName(uri) + ".xlsx", "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        MyExcelUtil.writeSimpleBySheet(outputStream, data, head, null);
        response.setContentType("application/octet-stream");
        outputStream.flush();
        outputStream.close();
        return WC_COMPLETED;
    }

    public static final String HTML_BEFORE = "<html>\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>FORM</title>\n" +
            "    <!-- Le styles -->\n" +
            "    <link href=\"/bootcss-p-layoutit_files/bootstrap-combined.min.css\" rel=\"stylesheet\">\n" +
            "    <link href=\"/bootcss-p-layoutit_files/layoutit.css\" rel=\"stylesheet\">\n" +
            "    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->\n" +
            "    <!--[if lt IE 9]>\n" +
            "    <script src=\"js/html5shiv.js\"></script>\n" +
            "    <![endif]-->\n" +
            "    <!-- Fav and touch icons -->\n" +
            "    <link rel=\"shortcut icon\" href=\"/bootcss-p-layoutit_files/favicon.png\">\n" +
            "    <script type=\"text/javascript\" src=\"/bootcss-p-layoutit_files/jquery-2.0.0.min.js.download\"></script>\n" +
            "    <!--[if lt IE 9]>\n" +
            "    <script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.9.1.min.js\"></script>\n" +
            "    <![endif]-->\n" +
            "    <script type=\"text/javascript\" src=\"/bootcss-p-layoutit_files/bootstrap.min.js.download\"></script>\n" +
            "    <script type=\"text/javascript\" src=\"/bootcss-p-layoutit_files/jquery-ui.js.download\"></script>\n" +
            "    <script type=\"text/javascript\" src=\"/bootcss-p-layoutit_files/jquery.ui.touch-punch.min.js.download\"></script>\n" +
            "    <script type=\"text/javascript\" src=\"/bootcss-p-layoutit_files/jquery.htmlClean.js.download\"></script>\n" +
            "</head>\n" +
            "<body style=\"min-height: 497px; cursor: auto;\" class=\"devpreview sourcepreview\">";


    public static final String HTML_AFTER = "</body></html>";

    public static final String CW_ERROR = "错误(ERROR)";
    public static final String WC_COMPLETED = "完成(COMPLETED)";

}


