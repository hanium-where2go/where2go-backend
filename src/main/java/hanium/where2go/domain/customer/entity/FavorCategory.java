package hanium.where2go.domain.customer.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "favor_category")
@NoArgsConstructor
@AllArgsConstructor
public class FavorCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "favor_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
