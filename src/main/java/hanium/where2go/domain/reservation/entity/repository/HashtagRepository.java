package hanium.where2go.domain.reservation.entity.repository;

import hanium.where2go.domain.reservation.entity.Hashtag;
import hanium.where2go.domain.reservation.entity.ReviewHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {


}
