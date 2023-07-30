package hanium.where2go.domain.reservation.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PredefinedHashtags {
    넓어요("넓어요"),
    조용해요("조용해요"),
    활기차요("활기차요"),
    가성비("가성비"),

    맛있어요("맛있어요"),

    친절해요("친절해요"),

    데이트코스("데이트코스"),

    단체방문("단체방문"),

    생파맛집("생파맛집")
    // 필요한 만큼 미리 정의된 해시태그들을 추가합니다.
    ;

    private final String hashtagName;
}