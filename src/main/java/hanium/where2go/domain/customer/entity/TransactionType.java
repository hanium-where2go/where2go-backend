package hanium.where2go.domain.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    EARN("적립"), USE("사용"), LOAD("충전");
    private final String key;
}
