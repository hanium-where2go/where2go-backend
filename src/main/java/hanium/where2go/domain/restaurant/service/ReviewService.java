package hanium.where2go.domain.restaurant.service;


import hanium.where2go.domain.reservation.dto.HashtagResponseDto;
import hanium.where2go.domain.reservation.dto.ReviewResponseDto;
import hanium.where2go.domain.reservation.entity.Review;
import hanium.where2go.domain.reservation.entity.ReviewHashtag;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.domain.restaurant.repository.ReviewRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Map<String,Long> hashtagCountMap = new HashMap<>();

        for(Review review : restaurant.getReview()){
            for(ReviewHashtag reviewHashtag : review.getReviewHashtags()){
                String hashtagName = reviewHashtag.getHashtag().getHashtagName();
                hashtagCountMap.put(hashtagName,hashtagCountMap.getOrDefault(hashtagName,0L)+1);
            }
        }

        List<String> top3Hashtags = hashtagCountMap.entrySet().stream()
                .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return new HashtagResponseDto(top3Hashtags);
    }

}



