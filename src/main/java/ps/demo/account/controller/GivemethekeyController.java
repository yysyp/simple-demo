package ps.demo.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.demo.account.service.LoginServiceImpl;
import ps.demo.dto.response.ErrorResponse;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class GivemethekeyController {

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @GetMapping("/givemethekey")
    public ResponseEntity<String> givemethekey() {
        String adminkey = loginServiceImpl.givemethekey();
        String body = "{'admin': '"+adminkey+"'}";
        return new ResponseEntity<String>(body, HttpStatus.OK);
    }
}
