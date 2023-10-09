package hanium.where2go.domain.reservation.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "review")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewHashtag> reviewHashtags = new ArrayList<>();

    private Double rate;
    private String content;


    //생성 메서드
    public static Review createReview(Reservation reservation, List<ReviewHashtag> reviewHashtags, double rate, String content) {
        Review review = new Review();
        review.rate = rate;
        review.content = content;
        review.reservation = reservation;
        for (ReviewHashtag reviewHashtag : reviewHashtags) {
            reviewHashtag.setReview(review);
        }
        review.reviewHashtags.addAll(reviewHashtags);
        return review;
    }

}
