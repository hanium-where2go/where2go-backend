package hanium.where2go.domain.reservation.entity;

public enum Rejection {

    FULL_SEATS("좌석이 다 찼습니다"),
    CLOSED("가게가 닫았습니다"),
    BREAK_TIME("가게의 브레이크 타임입니다"),
    ABOUT_TO_CLOSE("가게가 곧 닫을 예정입니다");


    private final String description;

    Rejection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}

