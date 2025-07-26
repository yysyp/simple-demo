package ps.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ps.demo.dto.response.HealthzResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Controller
@RequestMapping("/healthz")
public class HealthzController {

    @Autowired
    DataSource dataSource;

    @GetMapping
    @ResponseBody
    public HealthzResponse health(@RequestParam(name = "detail", required = false, defaultValue = "false")
                                          Boolean detail) {
        HealthzResponse healthzResponse = new HealthzResponse();

        healthzResponse.setData(new HealthzResponse.Health(HealthzResponse.Status.UP));

        if (detail) {
            try (
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
            ) {
                statement.execute("SELECT 1");
                healthzResponse.setDb("OK");
            } catch (Exception ex) {
                healthzResponse.setDb(ex.getMessage());
            }
        }

        return healthzResponse;
    }

}
