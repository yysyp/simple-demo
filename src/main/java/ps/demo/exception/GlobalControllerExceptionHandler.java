package ps.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ps.demo.dto.response.ErrorResponse;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<ErrorResponse> handleException(ServletRequestBindingException e) {
        log.error("--->>ServletRequestBindingException handling, message={}", e.getMessage(), e);
        BadRequestException bre = new BadRequestException(CodeEnum.BAD_REQUEST, false, e);
        return new ResponseEntity<ErrorResponse>(bre.toErrorResponse(), HttpStatus.valueOf(bre.getCodeEnum().getHttpCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Exception e) {
        log.error("--->>Exception handleThrowable, message={}", e.getMessage(), e);
        ErrorResponse errorResponse = null;
        ApiBaseException abe = null;
        if (e instanceof ApiBaseException) {
            abe = (ApiBaseException) e;
            errorResponse = abe.toErrorResponse();
        } else {
            abe = new ServerApiException(CodeEnum.INTERNAL_SERVER_ERROR, false, e);
            errorResponse = abe.toErrorResponse();
        }
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(abe.getCodeEnum().getHttpCode()));
    }

}