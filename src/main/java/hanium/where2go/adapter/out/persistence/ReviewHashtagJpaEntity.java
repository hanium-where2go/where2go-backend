package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "review_hashtag")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewHashtagJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "review_hashtag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewJpaEntity review;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashtagJpaEntity hashtag;
}
