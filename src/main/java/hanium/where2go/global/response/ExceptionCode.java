package hanium.where2go.global.response;

/**
 * 필요한 예외 코드 직접 정의하여 사용하시면 됩니다.
 * Example: throw new BaseException(ExceptionCode.USER_NOT_FOUND);
 */
public enum ExceptionCode {
    USER_NOT_FOUND(404, "User does not found."),
    DUPLICATED_USER(400,"User already exists."),
    RESTAURANT_NOT_FOUND(404, "Restaurant not found."),
    EMAIL_SERVER_ERROR(500, "Email server error has occurred."),
    EMAIL_NOT_FOUND(404, "Email address is invalid."),
    TOKEN_INVALID(401, "Token is invalid.");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
