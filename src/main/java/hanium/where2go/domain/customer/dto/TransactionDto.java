package hanium.where2go.domain.customer.dto;

import hanium.where2go.domain.customer.entity.Transaction;
import hanium.where2go.domain.customer.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private LocalDateTime createdAt;
    private TransactionType type;
    private String description;
    private int amount;

    public TransactionDto(Transaction transaction) {
        this.createdAt = transaction.getCreatedAt();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
    }
}
