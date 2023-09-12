package hanium.where2go.domain.reservation.controller;

import hanium.where2go.domain.reservation.entity.ReservationStatus;
import hanium.where2go.domain.restaurant.dto.ReservationUpdateMessage;
import hanium.where2go.domain.restaurant.dto.ReservationUpdateResultMessage;
import hanium.where2go.domain.restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // 서버에서 클라이언트로, 클라이언트에서 서버로 메세지를 보낼 수 있다
    private final ReservationService reservationService;


    // 클라이언트가 예약을 허용하거나 거절할 때 메시지를 받는 핸들러
    // 사장님이 서버에 예약을 거절할건지 수락할건지 보내게 된다
    @MessageMapping("/reservation/update")
    public void updateReservationStatus(ReservationUpdateMessage message) {
        // 예약 업데이트 로직 수행
        if ("REFUSED".equals(message.getStatus())) {
            // 거절 사유를 클라이언트에게 보내기
            String rejectionMessage = "예약이 거절되었습니다. 사유: " + message.getRejection();
            reservationService.updateReservationStatus(message.getReservationId(),ReservationStatus.REFUSED);
            messagingTemplate.convertAndSend("/topic/reservation", new ReservationUpdateResultMessage(rejectionMessage));
        }

        if ("COMPLETED".equals(message.getStatus())) {
            // 예약 상태를 업데이트하고 예약 번호 생성
            reservationService.updateReservationStatus(message.getReservationId(), ReservationStatus.COMPLETED);
        }
    }
}