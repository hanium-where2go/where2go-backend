package hanium.where2go.domain.reservation.controller;

import hanium.where2go.domain.reservation.dto.HashtagResponseDto;
import hanium.where2go.domain.reservation.dto.ReviewResponseDto;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.service.RestaurantService;
import hanium.where2go.domain.restaurant.service.ReviewService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReviewService reviewService;
    private final RestaurantService restaurantService;



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

//    // 사용자의 예약 요청
//    @PostMapping("/restaurants/{restaurantId}/reservation")
//    public ResponseEntity<BaseResponse<ReservationDto.ReservationResponseDto>> makeReservation(
//            @PathVariable("restaurantId") Long restaurantId,
//            @RequestBody ReservationDto.ReservationRequestDto reservationRequestDto) {
//
//        ReservationDto.ReservationResponseDto responseDto = reservationService.processReservation(reservationRequestDto);
//
//        // WebSocket을 통해 예약 결과를 클라이언트에게 전송
//        webSocketController.sendReservationResponse(responseDto);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 예약 내역", responseDto));
//    }
//
//    // 사장님의 예약 상태 변경
//    @PatchMapping("/reservations/{reservationId}")
//    public ResponseEntity<BaseResponse> editReservation(@PathVariable("reservationId") Long reservationId,
//                                                        @RequestBody ReservationDto.ReservationRequestDto requestDto) {
//        // 사장님의 결정을 처리하고 업데이트된 예약 정보를 반환
//        ReservationDto.ReservationResponseDto responseDto = reservationService.processOwnerDecision(reservationId, requestDto);
//
//        // WebSocket을 통해 예약 결과를 클라이언트에게 전송
//        webSocketController.sendReservationResponse(responseDto);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new BaseResponse<>(HttpStatus.OK.value(), "사장님 예약 상태가 변경되었습니다.", null));
//    }
}
