package hanium.where2go.domain.customer.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "point")
@NoArgsConstructor
@AllArgsConstructor
public class Point extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private int amount;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void earn(int amount) {
        this.amount += amount;
    }

    public void use(int amount) {
        if (this.amount - amount < 0) {
            throw new BaseException(ExceptionCode.POINT_NOT_ENOUGH);
        }
        this.amount -= amount;
    }

    public void load(int amount) {
        this.amount += amount;
    }
}
