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
    INVALID_EMAIL(404, "Email address is invalid."),
    INVALID_TOKEN(401, "Token is invalid."),
    EXPIRED_TOKEN(401, "Expired Token."),
    TOKEN_REISSUE_FAILED(400, "Token reissue failed."),
    UNAUTHENTICATED_USER(403, "User does not authenticated."),
    UNAUTHORIZED_USER(401,"User does not authorized."),
    CATEGORY_NOT_FOUND(404, "Category does not found."),
    LIQUOR_NOT_FOUND(404, "Liquor does not found."),
    EVENT_NOT_FOUND(404,"Event does not found"),
    RESERVATION_NOT_FOUND(404,"Reservation does not found"),
    POINT_NOT_ENOUGH(409, "Not enough point"),
    DUPLICATED_FAVOR_CATEGORY(400,"Favor Category already exists."),
    DUPLICATED_FAVOR_LIQUOR(400,"Favor Category already exists."),

    MENU_NOT_FOUND (404,"Menu not found"),

    MENU_BOARD_NOT_FOUND(404,"Menu Board not found"),

    CUSTOMER_NOT_FOUND(404,"Customer not found"),

    STORE_CLOSED(400,"Store is closed. Not able to make Reservation"),

    ALREADY_RESTAURANT_KEY_EXISTS(400,"RestaurantId is already exists"),

    CANNOT_FIND_RESTAURANT_KEY(400,"cannot find restaurant key");

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
