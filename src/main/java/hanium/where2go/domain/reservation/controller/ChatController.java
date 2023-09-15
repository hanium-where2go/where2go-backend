package hanium.where2go.domain.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired // ObjectMapper를 주입
    private ObjectMapper objectMapper;

    @MessageMapping("/seats")
    public void message(Seats seats) {
        try {
            // Seats 객체를 JSON 문자열로 직렬화하여 /sub 주제로 전송
            String jsonSeats = objectMapper.writeValueAsString(seats);
            messagingTemplate.convertAndSend("/sub/seats", jsonSeats);

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }
    }
}