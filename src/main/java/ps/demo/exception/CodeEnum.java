package ps.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * Define all the error code, error info.
 * When throwing exception, use:
 * throw new BadRequestException(CodeEnum.USER_INVALID);
 */
public enum CodeEnum {
    SUCCESS("200", 200),

    // Client Error
    BAD_REQUEST("400", 200),
    UNAUTHORIZED("401", 200),
    FORBIDDEN("403", 200),
    NOT_FOUND("404", 200),
    METHOD_NOT_ALLOWED("405", 200),
    REQUEST_TIMEOUT("408", 200),
    UNSUPPORTED_MEDIA_TYPE("415", 200),

    PAYLOAD_TOO_LARGE("413", 200),//HttpStatus.PAYLOAD_TOO_LARGE.value()),
    TOO_MANY_REQUESTS("429", 200),//HttpStatus.TOO_MANY_REQUESTS.value()),

    // Server Error
    INTERNAL_SERVER_ERROR("500", 200),
    NOT_IMPLEMENTED("501", 200),

    DUPLICATED_KEY("10000", 200),

    NO_KEMU_TYPE("10001", 200);

    private String code;
    private int httpCode;

    CodeEnum(String code, int httpStatus) {
        this.code = code;
        this.httpCode = httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public int getHttpCode() {
        return this.httpCode;
    }


}
