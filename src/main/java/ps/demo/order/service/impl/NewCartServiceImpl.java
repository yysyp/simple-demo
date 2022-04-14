package ps.demo.order.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.order.converter.OrderConverter;
import ps.demo.order.dto.NewCartReq;
import ps.demo.order.dto.NewCartRes;
import ps.demo.order.entity.NewCart;
import ps.demo.order.repository.NewCartRepository;
import ps.demo.order.service.NewCartService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yunpeng.song
 */
@Slf4j
@Service
public class NewCartServiceImpl implements NewCartService {

    @Autowired
    NewCartRepository newCartRepository;

    @Override
    public NewCartRes findTheCartByAccountId(Long accountId) {
        log.info("---find cart by accountId, accountId={}", accountId);
        NewCart newCart = newCartRepository.findTheCartByAccountId(accountId);
        if (newCart == null) {
            return null;
        }
        NewCartRes newCartRes = OrderConverter.convert(newCart);
        return newCartRes;
    }


    @Transactional
    @Override
    public NewCartRes addNewCart(NewCartReq newCartReq) {
        log.info("---add new cart, newCartReq={}", newCartReq);
        NewCart newCart = OrderConverter.convert(newCartReq);
        NewCart result = newCartRepository.save(newCart);
        NewCartRes newCartRes = OrderConverter.convert(result);
        return newCartRes;
    }


}
