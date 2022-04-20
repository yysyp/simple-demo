package ps.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(-2147483646) //The 3rd outermost order.
public class ApplicationJsonRequestSizeLimitFilter extends OncePerRequestFilter {

    @Value("${json.request-size-limit}")
    private long requestSizeLimit;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (isApplicationJson(request) && request.getContentLengthLong() > requestSizeLimit) {
            throw new BadRequestException(CodeEnum.PAYLOAD_TOO_LARGE);
            //"My JsonLimit Request content exceeded limit of ["+requestSizeLimit+"] bytes");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isApplicationJson(HttpServletRequest httpRequest) {
        try {
            return (MediaType.APPLICATION_JSON.isCompatibleWith(MediaType
                    .parseMediaType(httpRequest.getHeader(HttpHeaders.CONTENT_TYPE))));
        } catch (Exception e) {
            log.info("ApplicationJson limit err={}", e.getMessage());
            return false;
        }
    }
}
