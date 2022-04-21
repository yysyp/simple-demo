

package ps.demo.qnpoll.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import ps.demo.annotation.OperLog;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageResData;
import ps.demo.order.dto.NewCartRes;
import ps.demo.qn.dto.QuestionnaireDto;
import ps.demo.qn.dto.QuestionnaireReq;
import ps.demo.qn.dto.QuestionnaireResponseDto;
import ps.demo.qn.service.QuestionnaireResponseServiceImpl;
import ps.demo.qn.service.QuestionnaireServiceImpl;
import ps.demo.util.MyBeanUtil;
import ps.demo.util.MyDbUtils;
import ps.demo.util.MyJsonUtil;
import ps.demo.util.MyReadWriteUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/qnpoll")
public class PollController extends MyBaseController {

    @Autowired
    private QuestionnaireServiceImpl questionnaireserviceimpl;

    @Autowired
    private QuestionnaireResponseServiceImpl questionnaireresponseserviceimpl;

    @Value("${dir.upload-folder}")
    private String uploadDir;

    @GetMapping(value = "/{uri:[a-zA-Z0-9]+}")
    //@GetMapping(value = "/{uri}")
    public ResponseEntity showQuestionnaire(@PathVariable String uri, HttpServletRequest request,
                                            HttpServletResponse response) {
        //HtmlUtils.htmlEscape(uri);
        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findByURi(uri);
        StringBuffer html = new StringBuffer(HTML_BEFORE);
        File file = new File(uploadDir, questionnaireDto.getLayoutitContent());
        html.append(MyReadWriteUtil.readFileContent(file, "UTF-8"));
        html.append(HTML_AFTER);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        ResponseEntity<String> responseEntity = new ResponseEntity(html, headers, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping(value = "/{uri:[a-zA-Z0-9]+}/resp")
    public String questionnaireResponse(@PathVariable String uri, HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        if (CollectionUtils.isEmpty(parameters)) {
            return CW_ERROR;
        }

        QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
        questionnaireResponseDto.setUri(uri);
        questionnaireResponseDto.setResponseContent(MyJsonUtil.object2Json(parameters));
        questionnaireresponseserviceimpl.save(questionnaireResponseDto);

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


