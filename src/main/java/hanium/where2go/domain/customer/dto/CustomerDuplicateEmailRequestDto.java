package hanium.where2go.domain.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDuplicateEmailRequestDto {

    private String email;
}
