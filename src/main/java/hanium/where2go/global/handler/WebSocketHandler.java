package hanium.where2go.global.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ReservationService reservationService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        // 클라이언트로부터 받은 메시지를 파싱하여 예약 승인 또는 거절 동작을 구분합니다.
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        String action = jsonNode.get("action").asText();
        Long reservationId = jsonNode.get("reservationId").asLong();

        if ("approve".equals(action)) {
            // 예약 승인 동작 처리
            ReservationDto.ReservationResponseDto responseDto = reservationService.approveReservation(reservationId);

            // 클라이언트에게 응답 전송
            sendResponseToClient(session, responseDto);
        } else if ("reject".equals(action)) {
            // 예약 거절 동작 처리
            String rejectionReason = jsonNode.get("rejectionReason").asText();
            ReservationDto.ReservationResponseDto responseDto = reservationService.rejectReservation(reservationId, rejectionReason);

            // 클라이언트에게 응답 전송
            sendResponseToClient(session, responseDto);
        }
    }

    // 클라이언트에게 응답을 보내는 메서드
    private void sendResponseToClient(WebSocketSession session, ReservationResponseDto responseDto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseDto);

        // 클라이언트에게 응답 전송
        session.sendMessage(new TextMessage(jsonResponse));
    }
}
