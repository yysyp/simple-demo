package ps.demo.order.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import ps.demo.order.dto.CartLineDto;
import ps.demo.order.dto.NewCartReq;
import ps.demo.order.dto.NewCartRes;
import ps.demo.order.entity.CartLine;
import ps.demo.order.entity.NewCart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yunpeng.song
 */
public class OrderConverter {

    public static NewCart convert(NewCartReq newCartReq) {
        if (newCartReq == null) {
            return null;
        }
        NewCart newCart = new NewCart();
        BeanUtils.copyProperties(newCartReq, newCart);
        List<CartLineDto> cartLineDtoList = newCartReq.getCartLineList();
        if (CollectionUtils.isNotEmpty(cartLineDtoList)) {
            List<CartLine> cartLineList = cartLineDtoList.stream().map(e -> {
                CartLine cartLine = new CartLine();
                BeanUtils.copyProperties(e, cartLine);
                cartLine.setNewCart(newCart);
                return cartLine;
            }).collect(Collectors.toList());
            newCart.setCartLineList(cartLineList);
        }
        return newCart;
    }


    public static NewCartRes convert(NewCart newCart) {
        if (newCart == null) {
            return null;
        }
        NewCartRes newCartRes = new NewCartRes();
        BeanUtils.copyProperties(newCart, newCartRes);
        if (CollectionUtils.isNotEmpty(newCart.getCartLineList())) {
            List<CartLineDto> cartLineDtoList = newCart.getCartLineList().stream().map(e -> {
                CartLineDto dto = new CartLineDto();
                BeanUtils.copyProperties(e, dto);
                dto.setCartId(newCart.getId());
                return dto;
            }).collect(Collectors.toList());
            newCartRes.setCartLineList(cartLineDtoList);
        }
        return newCartRes;
    }

}
