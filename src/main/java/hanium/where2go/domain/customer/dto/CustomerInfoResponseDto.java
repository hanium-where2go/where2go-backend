package hanium.where2go.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInfoResponseDto {

    private String name;
    private String nickname;
    private String email;
    private String phoneNumber;
}
