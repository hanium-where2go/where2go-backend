package hanium.where2go.domain.restaurant.dto;

public class ReservationUpdateMessage {
    private Long reservationId; // 예약 ID
    private String status; // 업데이트할 예약 상태(COMPLETED 또는 REFUSED)
    private String rejection; // 거절 사유 (거절 시에만 필요)

    // 생성자, 게터, 세터 등 필요한 메서드를 추가할 수 있습니다.

    // 생성자
    public ReservationUpdateMessage() {
    }

    // 게터 및 세터 메서드
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejection() {
        return rejection;
    }

    public void setRejection(String rejection) {
        this.rejection = rejection;
    }
}
