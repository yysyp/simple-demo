package ps.demo.order.service;


import ps.demo.order.dto.NewCartReq;
import ps.demo.order.dto.NewCartRes;

/**
 * @author yunpeng.song
 */
public interface NewCartService {

    NewCartRes findTheCartByAccountId(Long accountId);

    NewCartRes addNewCart(NewCartReq newCartReq);

}
