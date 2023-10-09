package hanium.where2go.domain.reservation.entity;

import hanium.where2go.domain.BaseEntity;
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
public class ReviewHashtag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    //생성 메서드
    public static ReviewHashtag createReviewHashtag(Hashtag hashtag) {
        return ReviewHashtag.builder()
            .hashtag(hashtag)
            .build();
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
