package ps.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ps.demo.util.SwingTool;

@Slf4j
@SpringBootApplication
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(WebServerApplication.class);
//        springApplication.setWebApplicationType(WebApplicationType.NONE);
//        SwingTool.main(args);
//        springApplication.run(args);

    }

}
