package hanium.where2go.domain.restaurant.dto;

public class ReservationUpdateResultMessage {
    private String message;

    public ReservationUpdateResultMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}