package ps.demo.exception;


import org.apache.commons.lang3.exception.ExceptionUtils;
import ps.demo.dto.response.ErrorResponse;
import ps.demo.util.MyHeaderUtil;
import ps.demo.util.MyRequestContextUtil;
import ps.demo.util.MyTimeUtil;

public class ApiBaseException extends RuntimeException {
    private static final long serialVersionUID = 5391996017578973640L;
    private final CodeEnum codeEnum;
    private final boolean retryable;

    public ApiBaseException(CodeEnum codeEnum) {
        super(codeEnum.getCode());
        this.codeEnum = codeEnum;
        this.retryable = false;
    }

    public ApiBaseException(CodeEnum codeEnum, boolean retryable) {
        super(codeEnum.getCode());
        this.codeEnum = codeEnum;
        this.retryable = retryable;
    }

    public ApiBaseException(CodeEnum codeEnum, boolean retryable, String message) {
        super(message);
        this.codeEnum = codeEnum;
        this.retryable = retryable;
    }

    public ApiBaseException(CodeEnum codeEnum, boolean retryable, Throwable cause) {
        super(cause);
        this.codeEnum = codeEnum;
        this.retryable = retryable;
    }

    public ApiBaseException(CodeEnum codeEnum, boolean retryable, String message, Throwable cause) {
        super(message, cause);
        this.codeEnum = codeEnum;
        this.retryable = retryable;
    }


    public CodeEnum getCodeEnum() {
        return this.codeEnum;
    }

    public boolean isRetryable() {
        return this.retryable;
    }

    public ErrorResponse toErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(codeEnum.getCode());
        errorResponse.setMessage(this.getMessage());
        if (MyHeaderUtil.isTrace()) {
            String trace = ExceptionUtils.getStackTrace(this);
            errorResponse.setTrace(trace);
        }
        errorResponse.setTimestamp(MyTimeUtil.getNowStryMdTHmsS());
        errorResponse.setPath(MyRequestContextUtil.getRequest().getPathInfo());
        return errorResponse;
    }

}
