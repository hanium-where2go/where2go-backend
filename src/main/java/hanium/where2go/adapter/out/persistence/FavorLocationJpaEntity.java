package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@Table(name = "favor_location")
@NoArgsConstructor
@AllArgsConstructor
public class FavorLocationJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "favor_location_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;

    private String location;
    private BigDecimal longitude;
    private BigDecimal latitude;
}
