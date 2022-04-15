package ps.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.common.MyBaseController;
import ps.demo.util.MyTimeUtil;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "This is a greeting controller")
@Slf4j
@Controller
@RequestMapping("/api/greeting/greeting-tmlf")
public class GreetingController extends MyBaseController {

    @Operation(summary = "To query and list data")
    @GetMapping
    public ModelAndView query(Model model) {
        model.addAttribute("title", "Query Data:");
        //model.addAttribute("orderTrackingQueryRequest", new OrderTrackingQueryRequest());
        //MyPageResponse<OrderTrackingDto> myPageResponse = new MyPageResponse<>(new ArrayList<>());

        Map greeting = new HashMap();
        greeting.put("name", "GreetingController");
        greeting.put("date", MyTimeUtil.getNowStryMdTHmsS());
        model.addAttribute("greeting", greeting);
        //Render templates/greeting.html page
        return new ModelAndView("greeting", "greeting", greeting);
        //return new ModelAndView("redirect:/api/system/order-tracking-tmlf");
    }


}
