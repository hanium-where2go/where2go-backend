package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.reservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {


    @Query("SELECT rh.hashtag.hashtagName, COUNT(rh) AS count FROM ReviewHashtag rh GROUP BY rh.hashtag.hashtagName ORDER BY count DESC")
    List<Object[]> findTop3Hashtags();
}
