package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
public class MenuJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantJpaEntity restaurant;

    private String name;
    private int price;
    private String content;
    private String imgUrl;
}
