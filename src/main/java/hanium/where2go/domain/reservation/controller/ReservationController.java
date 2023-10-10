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
    @PostMapping("/restaurants/{restaurantId}/{customerId}/reservation")
    public ResponseEntity<BaseResponse> makeReservation(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("customerId") Long customerId,
            @RequestBody ReservationDto.ReservationRequestDto reservationRequestDto) {

        // 예약 요청을 서비스 레이어로 전달하여 처리
     ReservationDto.ReservationResponseDto reservationResponseDto =    reservationService.processReservation(restaurantId,customerId, reservationRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "예약 요청이 접수중입니다", reservationResponseDto));
    }

    // 사장님의 예약 승인/거절
    @PatchMapping("/reservations/{restaurantId}/{reservationId}")
    public ResponseEntity<BaseResponse> setReservationStatus(@PathVariable("restaurantId") Long restaurantId,
                                                             @PathVariable("reservationId") Long reservationId,
                                                             @RequestBody ReservationDto.updateReservationStatus updateReservationStatus){
        reservationService.updateReservationStatus(restaurantId,reservationId,updateReservationStatus);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(),"예약 결과가 업데이트 되었습니다", null));
    }

    // 예약 내용 조회
    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<BaseResponse<ReservationDto.ReservationInformationResponseDto>> searchReservation(@PathVariable("reservationId") Long reservationId){

        ReservationDto.ReservationInformationResponseDto reservationInformationResponseDto = reservationService.searchReservation(reservationId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "예약 상세 내역입니다", reservationInformationResponseDto));
    }
}
