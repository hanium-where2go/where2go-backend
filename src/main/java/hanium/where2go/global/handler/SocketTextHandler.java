package hanium.where2go.global.handler;

import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.entity.ReservationStatus;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private final ReservationRepository reservationRepository;

    @Autowired
    public SocketTextHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @MessageMapping("/updateReservationStatus")
    @SendTo("/topic/reservation/update")
    public WebSocketResponse updateReservationStatus(WebSocketRequest request) throws Exception {
        String action = request.getAction();
        Long reservationId = request.getReservationId();

        if ("complete".equals(action)) {
            // 클라이언트가 "complete" 메시지를 보냈을 때 처리
            // 예약 엔터티 업데이트: 예약 상태를 "COMPLETED"로 설정
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new Exception("예약을 찾을 수 없습니다."));

            reservation.setStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);

            // 예약 완료 응답 생성
            return new WebSocketResponse(
                    "예약 완료",
                    "99612390", // 예약 확인 번호
                    null, // 거절 사유
                    reservation.getId() // 예약 ID
            );
        } else if ("refuse".equals(action)) {
            // 클라이언트가 "refuse" 메시지를 보냈을 때 처리
            // 예약 엔터티 업데이트: 예약 상태를 "REFUSED"로 설정, 거절 사유 설정
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new Exception("예약을 찾을 수 없습니다."));

            reservation.setStatus(ReservationStatus.REFUSED);
            reservation.setRejection("곧 마감 예정"); // 예약 거절 사유 설정
            reservationRepository.save(reservation);

            // 예약 불가 응답 생성
            return new WebSocketResponse(
                    "예약 불가",
                    null, // 예약 확인 번호
                    "곧 마감 예정", // 거절 사유
                    null // 예약 ID
            );
        } else {
            // 다른 메시지에 대한 처리
            // 예를 들어, 기타 메시지에 대한 로직을 추가할 수 있음
            return null; // 현재는 null 반환, 필요에 따라 다른 응답을 생성
        }
    }
}