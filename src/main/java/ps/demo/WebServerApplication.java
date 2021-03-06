package ps.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

import javax.sql.DataSource;
//import ps.demo.util.SwingTool;

@Slf4j
@SpringBootApplication
public class WebServerApplication { //implements CommandLineRunner {

    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        //long freeMemory = Runtime.getRuntime().freeMemory();
        log.info("--->>maxMemory={}m, totalMemory={}m, usedMemory={}m",
                maxMemory / 1024 / 1024, totalMemory / 1024 / 1024,
                (maxMemory - totalMemory) / 1024 / 1024);
        SpringApplication.run(WebServerApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(WebServerApplication.class);
//        springApplication.setWebApplicationType(WebApplicationType.NONE);
//        SwingTool.main(args);
//        springApplication.run(args);

    }

}
