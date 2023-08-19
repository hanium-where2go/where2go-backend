package hanium.where2go.global.response;

import lombok.Getter;

// r
@Getter
public class BaseErrorResponse{

    private final int status;
    private final String message;

    public BaseErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseErrorResponse(BaseException baseException) {
        this.status = baseException.getStatus();
        this.message = baseException.getMessage();
    }
}
