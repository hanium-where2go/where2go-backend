package hanium.where2go.domain.reservation.repository;

import hanium.where2go.domain.reservation.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query("select ht from Hashtag ht where ht.id in :hashtagIds")
    List<Hashtag> findByIds(@Param("hashtagIds") List<Long> hashtagIds);
}
