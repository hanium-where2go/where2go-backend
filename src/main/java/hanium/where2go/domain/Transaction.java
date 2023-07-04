package hanium.where2go.domain;

public class Transaction {

    private Long id;
    private User user;
    private Point point;
    //거래 타입 -> Enum
    private String type;
    private int amount;
    private String description;
}
