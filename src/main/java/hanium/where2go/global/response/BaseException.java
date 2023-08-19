package hanium.where2go.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseException extends RuntimeException {

    ExceptionCode exceptionCode;

    public BaseException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public int getStatus() {
        return exceptionCode.getStatus();
    }

    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
