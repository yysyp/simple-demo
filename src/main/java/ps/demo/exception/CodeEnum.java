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
    BAD_REQUEST("400", 400),
    UNAUTHORIZED("401", 401),
    FORBIDDEN("403", 403),
    NOT_FOUND("404", 404),
    METHOD_NOT_ALLOWED("405", 405),
    REQUEST_TIMEOUT("408", 408),
    UNSUPPORTED_MEDIA_TYPE("415", 415),

    PAYLOAD_TOO_LARGE("413", HttpStatus.PAYLOAD_TOO_LARGE.value()),
    TOO_MANY_REQUESTS("429", HttpStatus.TOO_MANY_REQUESTS.value()),

    // Server Error
    INTERNAL_SERVER_ERROR("500", 500),
    NOT_IMPLEMENTED("501", 501),

    DUPLICATED_KEY("10000", 500);

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
