package hanium.where2go.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;

}
