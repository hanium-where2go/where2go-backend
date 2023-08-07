package hanium.where2go.domain.customer.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.liquor.entity.Liquor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "favor_liquor")
@NoArgsConstructor
@AllArgsConstructor
public class FavorLiquor extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "favor_liquor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
