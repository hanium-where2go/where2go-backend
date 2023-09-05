package hanium.where2go.global.handler;

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

        // 클라이언트로부터 받은 WebSocket 메시지를 처리합니다.
        // 이 부분에서 payload를 파싱하고 예약 승인 또는 거절 작업을 수행합니다.
        // 예를 들어, JSON 형식의 payload를 파싱하여 필요한 정보를 추출하고,
        // ReservationService를 사용하여 예약을 승인하거나 거절합니다.
        ObjectMapper objectMapper = new ObjectMapper();
        ReservationDto.ReservationResponseDto responseDto;

        try {
            // 클라이언트로부터 받은 JSON payload를 ReservationDto.ReservationRequestDto로 파싱
            ReservationDto.ReservationRequestDto requestDto = objectMapper.readValue(payload, ReservationDto.ReservationRequestDto.class);

            // 여기에서 requestDto를 사용하여 예약 승인 또는 거절 작업을 수행
            responseDto = reservationService.processReservation(requestDto);

        } catch (Exception e) {
            log.error("WebSocket 메시지 처리 중 오류 발생: " + e.getMessage());
            // 오류 처리 로직을 구현하고 예외 상황에 맞는 응답을 생성
            responseDto = createErrorResponse("WebSocket 메시지 처리 중 오류 발생");
        }

        // 처리 결과를 클라이언트로 전송
        sendResponseToClient(session, responseDto);
    }

    // 클라이언트로 응답을 보내는 메서드
    private void sendResponseToClient(WebSocketSession session, ReservationDto.ReservationResponseDto responseDto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseDto);

        // 클라이언트로 JSON 응답을 전송
        session.sendMessage(new TextMessage(jsonResponse));
    }

    // 오류 응답을 생성하는 메서드
    private ReservationDto.ReservationResponseDto createErrorResponse(String errorMessage) {
        ReservationDto.ReservationResponseDto responseDto = new ReservationDto.ReservationResponseDto();
        responseDto.setStatus(400);
        responseDto.setMessage(errorMessage);
        return responseDto;
    }
}
