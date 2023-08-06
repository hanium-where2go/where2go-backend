package hanium.where2go.domain.customer.entity;

import hanium.where2go.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavorLiquor> favorLiquors = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavorCategory> favorCategories = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavorLocation> favorLocations = new ArrayList<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Point point;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public void setPoint(Point point) {
        this.point = point;
        point.setCustomer(this);
    }

    public void earn(int amount, String description) {
        point.earn(amount);

        Transaction transaction = Transaction.builder()
            .amount(amount)
            .customer(this)
            .description(description)
            .type(TransactionType.EARN)
            .build();

        this.transactions.add(transaction);
    }

    public void use(int amount, String description) {
        point.use(amount);

        Transaction transaction = Transaction.builder()
            .amount(amount)
            .customer(this)
            .description(description)
            .type(TransactionType.USE)
            .build();

        this.transactions.add(transaction);
    }

    public void load(int amount, String description) {
        point.load(amount);

        Transaction transaction = Transaction.builder()
            .amount(amount)
            .customer(this)
            .description(description)
            .type(TransactionType.LOAD)
            .build();

        this.transactions.add(transaction);
    }
}
