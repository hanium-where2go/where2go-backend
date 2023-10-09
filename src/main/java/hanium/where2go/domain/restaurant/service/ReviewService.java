package hanium.where2go.domain.restaurant.service;


import hanium.where2go.domain.reservation.dto.HashtagResponseDto;
import hanium.where2go.domain.reservation.dto.ReviewResponseDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.domain.reservation.repository.ReviewRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;



    // 레스토랑의 리뷰 조회
    public List<ReviewResponseDto> searchReview(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        List<ReviewResponseDto> reviews = restaurant.getReview().stream()
                .map(review -> new ReviewResponseDto(
                        review.getContent(),
                        review.getReservation().getCustomer().getNickname(),
                        review.getReviewHashtags().stream()
                                .map(reviewHashtag -> reviewHashtag.getHashtag().getHashtagName())
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return reviews;
    }

    // 레스토랑의 해시태그 조회
    public HashtagResponseDto searchHashtag(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        List<Object[]> top3Hashtag = reviewRepository.findTop3Hashtags();
        List<String> top3HashtagNames = new ArrayList<>();

        int count = 0;
        for (Object[] data : top3Hashtag) { // top 3 를 뽑아야 하는데 빈도수가 같은 해시태그가 여러 개 나올 수 있기 때문에 3개로 제한
            String hashtagName = (String) data[0];
                top3HashtagNames.add(hashtagName);
                count++;

            if (count == 3) { // 빈도수가 같으면 배열의 앞쪽에 있는 내용 3개가 나옴
                break;
            }
        }

        return new HashtagResponseDto(top3HashtagNames);
    }

}



