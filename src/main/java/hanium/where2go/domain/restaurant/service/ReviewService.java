package hanium.where2go.domain.restaurant.service;


import hanium.where2go.domain.reservation.dto.ReviewResponseDto;
import hanium.where2go.domain.reservation.entity.Hashtag;
import hanium.where2go.domain.reservation.entity.PredefinedHashtags;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;


    public List<ReviewResponseDto> searchReview(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        List<ReviewResponseDto> reviews = restaurant.getReview().stream()
                .map(review -> new ReviewResponseDto(
                        review.getReservation().getCustomer().getName(),
                        review.getContent(),
                        review.getReviewHashtags().stream()
                                .map(reviewHashtag -> reviewHashtag.getHashtag().getReview_hashtagName().getHashtagName()) // List<PredefinedHashtags>를 List<String>으로 변환
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return reviews;
    }
}



