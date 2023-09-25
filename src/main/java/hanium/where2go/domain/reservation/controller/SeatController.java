package hanium.where2go.domain.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.global.redis.RedisUtil;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RestController
public class SeatController {

    private final RedisUtil redisUtil;

    // 처음에 사장님이 레스토랑의 아이디와 좌석수를 소켓으로 보내는 부분
    // messagePayload 를 통해서 json 형식으로 레스토랑의 아이디와 좌석수를 보내주면 레디스에 레스토랑 아이디로 구분해서 좌석수가 등록됨.
    @MessageMapping("/seats")
    @SendTo("/sub/seats")
    public Seats message(@Payload Map<String, Object> messagePayload) {
        try {
            // JSON 메시지에서 restaurantId와 seats 필드를 추출
            Integer receivedRestaurantId = (Integer) messagePayload.get("restaurantId");
            Integer seats = (Integer) messagePayload.get("seats");
            // restaurantId를 이용해 레디스 키를 생성
            String restaurantKey = String.valueOf(receivedRestaurantId);

            // 이미 key가 존재하는지 확인
            if (redisUtil.hasKey(restaurantKey)) {
                throw new BaseException(ExceptionCode.ALREADY_RESTAURANT_KEY_EXISTS);
            }

            redisUtil.set(restaurantKey + ":storeStatus", "OPEN", 2*60*60*1000); // 영업 상태 restaurantId 로 구분해서 저장
            redisUtil.set(restaurantKey, String.valueOf(seats), 2*60*60*1000); // 2시간(120분) 동안 유효

            // 잘 들어왔나 확인용
            Seats seatsObject = new Seats();
            seatsObject.setSeat(seats);
            seatsObject.setRestaurantId(restaurantKey); // 변수 타입 변경

            return seatsObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // 남은 좌석수를 확인하는 로직
    // messagePayload 를 이용해서 json 형식으로 /pub/restseats 를 할때에 restaurantId 를 같이 보내준다
    @MessageMapping("/restseats")
    @SendTo("/sub/restseats")
    public Integer restSeats(@Payload Map<String, Object> messagePayload) {
        try {
            // JSON 메시지에서 restaurantId를 추출
            Integer receivedRestaurantId = (Integer) messagePayload.get("restaurantId");

            // restaurantId를 이용하여 해당 가게의 레디스 상태 키 생성
            String restaurantKey = String.valueOf(receivedRestaurantId);

            if (!(redisUtil.hasKey(restaurantKey))) {
                throw new BaseException(ExceptionCode.CANNOT_FIND_RESTAURANT_KEY);
            }

            // Redis에서 가게 상태 가져오기
            String storeStatus = (String) redisUtil.get(restaurantKey+":storeStatus");

            // 가게 상태가 OPEN일 때만 좌석 수 가져오기
            if ("OPEN".equals(storeStatus)) {
                String totalSeatCountStr = (String) redisUtil.get(restaurantKey);
                // 문자열로 저장된 좌석 수를 Integer로 변환하여 반환
                if (totalSeatCountStr != null) {
                    return Integer.parseInt(totalSeatCountStr);
                }
            }
            // 가게가 OPEN 상태가 아니거나 좌석 수가 없으면 null 반환
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 사장님이 가게 닫는 요청을 한다
    // messagePayload 를 이용해서 json 형식으로 /pub/close 를 할때에 restaurantId 를 같이 보내준다
    @MessageMapping("/close")
    public void closeStore(@Payload Map<String,Object> messagePayload) {

        Integer receivedRestaurantId = (Integer) messagePayload.get("restaurantId");
        String restaurantKey = String.valueOf(receivedRestaurantId);

        // 가게 상태를 CLOSED로 업데이트
        redisUtil.set(restaurantKey + ":storeStatus", "CLOSED", 2 * 60 * 60 * 1000);
        redisUtil.delete(restaurantKey); // 가게 닫았으니까 키 삭제해주기
    }

    // 가게의 운영 상태를 볼 수 있다
    // messagePayload 를 이용해서 json 형식으로 /pub/status 를 할때에 restaurantId 를 같이 보내준다
    @MessageMapping("/status")
    @SendTo("/sub/status")
    public String closeOperationStatus(@Payload Map<String,Object> messagePayload) {

            Integer receivedRestaurantId = (Integer) messagePayload.get("restaurantId");
            String restaurantKey = String.valueOf(receivedRestaurantId);

            if (!(redisUtil.hasKey(restaurantKey))) { // 키를 갖고 있지 않다면 이미 문을 닫은것으로 판단
                return "STORE CLOSED";
            }

            String storeOperationStatus = (String) redisUtil.get(restaurantKey+":storeStatus");
            if ("OPEN".equals(storeOperationStatus)) {
                return "STORE OPEN";
            } else {
                return "STORE CLOSED";
            }

    }
}