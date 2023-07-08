package hanium.where2go.domain.customer.entity;

import hanium.where2go.domain.BaseEntity;
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
public class FavorLocation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "favor_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String location;
    private BigDecimal longitude;
    private BigDecimal latitude;
}
