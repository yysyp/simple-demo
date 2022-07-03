package ps.demo.config;

import com.alibaba.excel.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import ps.demo.account.helper.MyPrincipalUtils;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
@Order(-2147483647)
public class FilterWhiteList extends OncePerRequestFilter {

    @Value("${whitelist.enabled:true}")
    private boolean enabled;

    @Value("${whitelist.uris}")
    private List<String> uris;

    @Value("${whitelist.preciseUris}")
    private List<String> preciseUris;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!enabled) {
            filterChain.doFilter(request, response);
            return;
        }
        String uri = request.getRequestURI();
        boolean reject = true;
        if (null == MyPrincipalUtils.getCurrentUser(request)) {
            for (String whiteUri : uris) {
                whiteUri = whiteUri.trim();
                if (StringUtils.startsWithIgnoreCase(uri, whiteUri)) {
                    reject = false;
                    break;
                }
            }
        } else {
            reject = false;
        }

        if (reject) {
            for (String preciseUri : preciseUris) {
                if (uri.equalsIgnoreCase(preciseUri)) {
                    reject = false;
                    break;
                }
            }
        }
        if (reject) {
            throw new BadRequestException(CodeEnum.UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }
}
