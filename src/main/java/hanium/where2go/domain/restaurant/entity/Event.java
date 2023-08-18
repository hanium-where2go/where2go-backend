package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.restaurant.dto.RestaurantDto;
import hanium.where2go.domain.restaurant.dto.RestaurantEventDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String title;
    private String content;

    //레스토랑의 이벤트의 시작날짜와 끝나는 날짜 추가했음. 해당 날짜에 맞는 이벤트를 필터링해서 보여줘야 하기 때문
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void update(RestaurantEventDto.EventtUpdateRequestDto eventtUpdateRequestDto) {
       this.title = eventtUpdateRequestDto.getTitle();
       this.content = eventtUpdateRequestDto.getContent();
       this.startDate = eventtUpdateRequestDto.getStartDate().atStartOfDay();
       this.endDate = eventtUpdateRequestDto.getEndDate().atStartOfDay();
    }

    private <T> T updateField(T currentValue, T newValue) {
        return newValue != null ? newValue : currentValue;
    }

}
