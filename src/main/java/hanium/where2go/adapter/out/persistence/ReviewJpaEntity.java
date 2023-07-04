package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    private Double rate;
    private String content;

}
