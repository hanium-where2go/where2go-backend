package hanium.where2go.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object o, long ms) {
        redisTemplate.opsForValue().set(key, o, ms, TimeUnit.MILLISECONDS);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // 가게 상태를 초기화하고 Redis에 저장
    public void initializeStoreStatus() {
        redisTemplate.opsForValue().set("storeStatus", "CLOSED");
    }

    // 가게 상태를 업데이트하여 Redis에 저장
    public void updateStoreStatus(String newStatus) {
        redisTemplate.opsForValue().set("storeStatus", newStatus);
    }

    // 가게 상태를 가져오는 메서드
    public String getStoreStatus() {
        return (String) redisTemplate.opsForValue().get("storeStatus");
    }

    public void updateSeatCount(int seatCount) {
        redisTemplate.opsForValue().set("totalSeatCount", Integer.toString(seatCount));
    }
}
