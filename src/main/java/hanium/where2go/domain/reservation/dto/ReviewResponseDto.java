package hanium.where2go.domain.reservation.dto;


import hanium.where2go.domain.reservation.entity.Hashtag;
import hanium.where2go.domain.reservation.entity.PredefinedHashtags;
import hanium.where2go.domain.reservation.entity.ReviewHashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private String nickname;
    private String content;
    private List<String> hashtag;

}
