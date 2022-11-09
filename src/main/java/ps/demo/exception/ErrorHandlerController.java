package ps.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ps.demo.dto.response.DefaultResponse;
import ps.demo.util.MyHeaderUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ErrorHandlerController extends AbstractErrorController {

    @Value("${exception.bodyTrace}")
    private boolean exceptionBodyTrace;

    @Autowired
    public ErrorHandlerController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ResponseEntity<DefaultResponse> error(HttpServletRequest request) {
        log.info("--->>ErrorHandlerController error");
        BadRequestException exception = new BadRequestException(CodeEnum.NOT_FOUND);
        DefaultResponse errorResponse = exception.toErrorResponse();
        HttpStatus status = HttpStatus.valueOf(exception.getCodeEnum().getHttpCode());
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        options = options.including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.EXCEPTION})
                .including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.STACK_TRACE})
                .including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.MESSAGE})
                .including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.BINDING_ERRORS});
        Map<String, Object> body = this.getErrorAttributes(request, options);

        log.error("--->>Error handling, handleError, data={}", body);
        if (exceptionBodyTrace) {
            errorResponse.setTrace(body.toString());
        }
        errorResponse.setPath(request.getPathInfo());
        return new ResponseEntity<>(errorResponse, status);
    }

}