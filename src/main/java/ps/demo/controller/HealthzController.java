package ps.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ps.demo.dto.response.HealthzResponse;

import javax.sql.DataSource;

@Slf4j
@Controller
@RequestMapping("/healthz")
public class HealthzController {

    @GetMapping
    @ResponseBody
    public HealthzResponse health() {
        HealthzResponse healthzResponse = new HealthzResponse();

        healthzResponse.setData(new HealthzResponse.Health(HealthzResponse.Status.UP));

        return healthzResponse;
    }

}
