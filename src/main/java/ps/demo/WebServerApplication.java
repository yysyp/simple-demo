package ps.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ps.demo.util.SwingTool;

@Slf4j
@SpringBootApplication
public class WebServerApplication { //implements CommandLineRunner {

    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("--->>maxMemory={}, totalMemory={}, freeMemory={}", maxMemory, totalMemory, freeMemory);

        SpringApplication.run(WebServerApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(WebServerApplication.class);
//        springApplication.setWebApplicationType(WebApplicationType.NONE);
//        SwingTool.main(args);
//        springApplication.run(args);

    }

}
