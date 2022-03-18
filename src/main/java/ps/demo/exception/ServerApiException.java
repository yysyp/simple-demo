package ps.demo.exception;

public class ServerApiException extends ApiBaseException {

    public ServerApiException(CodeEnum codeEnum) {
        super(codeEnum);
    }

    public ServerApiException(CodeEnum codeEnum, boolean retryable) {
        super(codeEnum, retryable);
    }

    public ServerApiException(CodeEnum codeEnum, boolean retryable, String message) {
        super(codeEnum, retryable, message);
    }

    public ServerApiException(CodeEnum codeEnum, boolean retryable, Throwable cause) {
        super(codeEnum, retryable, cause);
    }

    public ServerApiException(CodeEnum codeEnum, boolean retryable, String message, Throwable cause) {
        super(codeEnum, retryable, message, cause);
    }
}
