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
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    public Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public OwnerJpaEntity owner;

    public String restaurantName;
    public String address;
    public String description;
    public String tel;
    public String businessRegistration;
    public int seat;
    public BigDecimal longitude;
    public BigDecimal latitude;
}