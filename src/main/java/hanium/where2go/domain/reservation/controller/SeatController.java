package hanium.where2go.domain.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.global.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class SeatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private ObjectMapper objectMapper;
    private final RedisUtil redisUtil;


    // 처음에 사장님이 좌석 등록
    @MessageMapping("/seats")
    @SendTo("/sub/seats")
    public Seats message(Seats seats) {
        try {
            // Seats 객체를 JSON 문자열로 직렬화하여 /sub 주제로 전송
            redisUtil.updateStoreStatus("OPEN");
            redisUtil.set("totalSeatCount", Integer.toString(seats.getSeat()), 2 * 60 * 60 * 1000); // 2시간(120분) 동안 유효

            // Redis에서 가게 상태 가져오기
            String storeStatus = redisUtil.getStoreStatus();

            // Redis에서 좌석 수 가져오기
            String totalSeatCount = (String) redisUtil.get("totalSeatCount");

            seats.setStatus(storeStatus);
            seats.setSeat(Integer.valueOf(totalSeatCount));

            return seats;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 남은 좌석을 볼 수 있다
    @MessageMapping("/restseats")
    @SendTo("/sub/restseats")
    public Integer restSeats() {
        try {
            // Redis에서 가게 상태 가져오기
            String storeStatus = redisUtil.getStoreStatus();

            // 가게 상태가 OPEN일 때만 좌석 수 가져오기
            if ("OPEN".equals(storeStatus)) {
                String totalSeatCount = (String) redisUtil.get("totalSeatCount");
                // 문자열로 저장된 좌석 수를 Integer로 변환하여 반환
                return Integer.valueOf(totalSeatCount);
            }
            // 가게가 OPEN 상태가 아니면 null 반환
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 사장님이 가게 닫는 요청
    @MessageMapping("/close")
    public void closeStore() {
        try {
            // 가게 상태를 CLOSED로 업데이트
            redisUtil.updateStoreStatus("CLOSED");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 가게의 운영 상태를 볼 수 있음
    @MessageMapping("/status")
    @SendTo("/sub/status")
    public String closeOperationStatus() {
        try {
            String storeOperationStatus = redisUtil.getStoreStatus();
            if ("OPEN".equals(storeOperationStatus)) {
                return "STORE OPEN";
            } else {
                return "STORE CLOSED";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR"; // 예외 발생 시 에러 메시지 반환
        }
    }
    //테스트
}