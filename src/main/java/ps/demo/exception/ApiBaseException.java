package ps.demo.exception;


import org.apache.commons.lang3.exception.ExceptionUtils;
import ps.demo.dto.response.DefaultResponse;
import ps.demo.util.MyHeaderUtil;
import ps.demo.util.MyRequestContextUtil;
import ps.demo.util.MyTimeUtil;

import javax.servlet.http.HttpServletRequest;

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

    public DefaultResponse toErrorResponse() {
        DefaultResponse errorResponse = new DefaultResponse();
        errorResponse.setCode(codeEnum.getCode());
        errorResponse.setMessage(this.getMessage());
        if (MyHeaderUtil.isTrace()) {
            String trace = ExceptionUtils.getStackTrace(this);
            errorResponse.setTrace(trace);
        }
        errorResponse.setTimestamp(MyTimeUtil.getNowStryMdTHmsS());
        HttpServletRequest request = MyRequestContextUtil.getRequest();
        if (request != null) {
            errorResponse.setPath(request.getPathInfo());
        }
        return errorResponse;
    }

}
