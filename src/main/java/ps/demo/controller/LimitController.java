package ps.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.demo.annotation.Limit;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/limit")
public class LimitController {

    @Operation(summary = "Throttling control demo, limit1")
    @GetMapping("/test1")
    @Limit(key = "limit1", permitsPerSecond = "${throttling.permitsPerSecond}", timeoutMs = "${throttling.timeoutMs}", timeunit = TimeUnit.MILLISECONDS, msg = "${throttling.msg}-1")
    public String limit1() {
        log.info("Permits bucket get limit1 success");
        return "ok";
    }

    @Operation(summary = "Throttling control demo, limit2")
    @GetMapping("/test2")
    @Limit(permitsPerSecond = "1.1")
    public String limit2() {
        log.info("Permits bucket get limit2 success");
        return "ok";
    }

}
