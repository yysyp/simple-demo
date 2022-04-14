package ps.demo.order.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ps.demo.annotation.OperLog;
import ps.demo.order.dto.NewCartReq;
import ps.demo.order.dto.NewCartRes;
import ps.demo.order.service.NewCartService;

import java.util.List;

/**
 * @author yunpeng.song
 */

@RestController
@RequestMapping(value = "/api/new-cart")
public class NewCartController {

    @Autowired
    NewCartService cartLineService;

    @Operation(summary = "New cart get find cart details")
    @PostMapping("/get")
    @OperLog(operModul = "cart", operType = "cart", operDesc = "find cart by accountId.")
    public NewCartRes findCartLineByAccountId(@RequestParam Long accountId) {
        NewCartRes cartLineRes = cartLineService.findTheCartByAccountId(accountId);
        return cartLineRes;
    }


    @Operation(summary = "New cart save cart details")
    @PostMapping("/save")
    @OperLog(operModul = "cart", operType = "cart", operDesc = "addNewCart.")
    public NewCartRes addNewCart(@Validated @RequestBody NewCartReq newCartReq) {
        NewCartRes cartLineRes = cartLineService.addNewCart(newCartReq);
        return cartLineRes;
    }


}
