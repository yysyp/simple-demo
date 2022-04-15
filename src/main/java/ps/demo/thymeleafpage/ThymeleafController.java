package ps.demo.thymeleafpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {

    ///tpage/thympage --> templates/thympage.html
    ///tpage/i18n--lang --> templates/i18n/lang.html
    @RequestMapping("/tpage/{thymeleafPage}")
    public String index(@PathVariable("thymeleafPage") String thymeleafPage) {
        thymeleafPage = thymeleafPage.replaceAll("\\-\\-", "/");
        return thymeleafPage;

    }

}
