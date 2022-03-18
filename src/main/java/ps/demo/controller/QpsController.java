package ps.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.demo.util.MyPfTiming;

@Slf4j
@RestController
@RequestMapping(value = "/api/qps")
public class QpsController {

    @GetMapping("/get")
    public String qpsGet() {
        MyPfTiming myPfTiming = MyPfTiming.start();

        long sleep = RandomUtils.nextLong(500, 3000);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            log.error("Interrupted ", e);
        }

        log.info("qps get response, cost={} ms", myPfTiming.timeMs());
        return "{\"name\": \"qps \\r\\n test " + java.time.LocalDateTime.now() + "newline2 \\nhahah\\ttab \", \"price\": " + sleep + "}";
    }

}
