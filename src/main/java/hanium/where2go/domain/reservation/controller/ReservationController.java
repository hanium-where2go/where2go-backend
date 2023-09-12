package hanium.where2go.domain.reservation.controller;

import hanium.where2go.domain.reservation.dto.HashtagResponseDto;
import hanium.where2go.domain.reservation.dto.ReviewResponseDto;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.service.ReservationService;
import hanium.where2go.domain.restaurant.service.RestaurantService;
import hanium.where2go.domain.restaurant.service.ReviewService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;


    @GetMapping("/restaurants/{restaurantId}/review")
    public ResponseEntity<BaseResponse<List<ReviewResponseDto>>> searchReviews(@PathVariable("restaurantId") Long restaurantId){

        List<ReviewResponseDto> reviews = reviewService.searchReview(restaurantId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "가게 리뷰를 불러왔습니다", reviews));

    }

    @GetMapping("/restaurants/{restaurantId}/hash-tag")
    public ResponseEntity<BaseResponse<HashtagResponseDto>> searchHashtags(@PathVariable("restaurantId") Long restaurantId){

        HashtagResponseDto hashtags = reviewService.searchHashtag(restaurantId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "가게 해시태그를 불러왔습니다", hashtags));
    }

    //고객의 예약 요청
    @PostMapping("/restaurants/{restaurantId}/reservation")
    public ResponseEntity<BaseResponse> makeReservation(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestBody ReservationDto.ReservationRequestDto reservationRequestDto) {

        // 예약 요청을 서비스 레이어로 전달하여 처리
        reservationService.processReservation(restaurantId, reservationRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "예약 요청이 접수중입니다", null));
    }

}
