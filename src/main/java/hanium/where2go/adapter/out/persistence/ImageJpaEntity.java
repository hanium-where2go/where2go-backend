package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
public class ImageJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewJpaEntity review;

    private String imgUrl;
}
