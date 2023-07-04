package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class EventJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantJpaEntity restaurant;

    private String title;
    private String content;
}
