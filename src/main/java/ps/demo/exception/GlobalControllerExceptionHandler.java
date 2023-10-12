package ps.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import ps.demo.dto.response.DefaultResponse;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<DefaultResponse> handleException(ServletRequestBindingException e) {
        log.error("--->>ServletRequestBindingException handling, message={}", e.getMessage(), e);
        BadRequestException bre = new BadRequestException(CodeEnum.BAD_REQUEST, false, e);
        return new ResponseEntity<DefaultResponse>(bre.toErrorResponse(), HttpStatus.valueOf(bre.getCodeEnum().getHttpCode()));
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<DefaultResponse> handleException(NoHandlerFoundException e) {
        log.error("--->>NoHandlerFoundException handling, message={}", e.getMessage(), e);
        BadRequestException bre = new BadRequestException(CodeEnum.BAD_REQUEST, false, e);
        return new ResponseEntity<DefaultResponse>(bre.toErrorResponse(), HttpStatus.valueOf(bre.getCodeEnum().getHttpCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleThrowable(Exception e) {
        log.error("--->>Exception handleThrowable, message={}", e.getMessage(), e);
        DefaultResponse errorResponse = null;
        ApiBaseException abe = null;
        if (e instanceof ApiBaseException) {
            abe = (ApiBaseException) e;
            errorResponse = abe.toErrorResponse();
        } else {
            abe = new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, false, e);
            errorResponse = abe.toErrorResponse();
        }
        return new ResponseEntity<DefaultResponse>(errorResponse, HttpStatus.valueOf(abe.getCodeEnum().getHttpCode()));
    }

}