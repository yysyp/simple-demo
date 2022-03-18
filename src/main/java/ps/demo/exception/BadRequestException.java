package ps.demo.exception;

public class BadRequestException extends ApiBaseException {

    public BadRequestException(CodeEnum codeEnum) {
        super(codeEnum);
    }

    public BadRequestException(CodeEnum codeEnum, boolean retryable) {
        super(codeEnum, retryable);
    }

    public BadRequestException(CodeEnum codeEnum, boolean retryable, String message) {
        super(codeEnum, retryable, message);
    }

    public BadRequestException(CodeEnum codeEnum, boolean retryable, Throwable cause) {
        super(codeEnum, retryable, cause);
    }

    public BadRequestException(CodeEnum codeEnum, boolean retryable, String message, Throwable cause) {
        super(codeEnum, retryable, message, cause);
    }
}
