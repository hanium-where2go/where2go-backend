package hanium.where2go.domain.reservation.service;

import hanium.where2go.domain.reservation.dto.ReservationRequestDto;
import hanium.where2go.domain.reservation.dto.ReservationResponseDto;
import hanium.where2go.domain.reservation.dto.ReservationStatus;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    private final JwtProvider jwtProvider;

    @Transactional
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }



    public ResponseEntity<ReservationResponseDto> create(ReservationRequestDto requestDto) {
        // ReservationRequestDto에서 필요한 데이터 추출



        // 예약 생성을 위한 Reservation 엔티티 생성
        Reservation reservation =  Reservation.builder()
                .restaurant(Restaurant.builder()
                        .restaurantId(requestDto.getRestaurandId())  // 직접 restaurantId에 값을 할당
                        .build())
                .numberOfPeople(requestDto.getNumberOfPeople())
                .reservationTime(requestDto.getReservationTime())
                .content(requestDto.getContent())
                .status(ReservationStatus.CANCELLED.name())
                .build();


        // 예약 저장
        reservationRepository.save(reservation);

        if (reservation.getStatus().equals(ReservationStatus.CONFIRMED.name())) {
            ReservationResponseDto responseDto = new ReservationResponseDto();
            responseDto.setStatus(ReservationStatus.CONFIRMED);
            responseDto.setReservationId(reservation.getId());
            responseDto.setConfirmNum(reservation.getConfirmNum());
            return ResponseEntity.ok(responseDto);
        } else {
            String rejectionReason = "사장님의 거절 사유 입니다";
            throw new BaseException(404,rejectionReason);
        }
    }

}
/*
 else {
         Owner owner = getCurrentOwner(); // 현재 로그인한 사장님 정보 가져오기
         owner.setRejectionReason("사장님의 거절 사유를 여기에 입력하세요");
         reservation.setOwner(owner); // Reservation 엔티티에 Owner 설정

         reservationRepository.save(reservation);

         ReservationResponseDto responseDto = new ReservationResponseDto();
         responseDto.setStatus(ReservationStatus.REJECTED);
         responseDto.setRejectionReason(owner.getRejectionReason());
         return ResponseEntity.ok(responseDto);
         }
         }

private Owner getCurrentOwner() {
        // 현재 로그인한 사장님 정보를 가져와서 반환하는 코드 작성
        // 예: 인증된 사용자 정보를 가져오는 SecurityContextHolder 이용
        // 이를 통해 Owner 엔티티를 조회하거나 OwnerService를 통해 현재 사용자의 정보를 가져올 수 있음
        // 해당 코드는 프로젝트의 인증 및 사용자 관리 시스템에 따라 구현 방식이 달라질 수 있음
        }
*/


