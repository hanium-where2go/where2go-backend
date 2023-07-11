package hanium.where2go.domain.customer.controller;

import hanium.where2go.domain.customer.dto.CustomerDuplicateEmailRequestDto;
import hanium.where2go.domain.customer.dto.CustomerDuplicateEmailResponseDto;
import hanium.where2go.domain.customer.dto.CustomerSignupRequestDto;
import hanium.where2go.domain.customer.service.CustomerService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public BaseResponse<String> signup(@RequestBody CustomerSignupRequestDto customerSignupRequestDto) {
        customerService.signup(customerSignupRequestDto);
        return new BaseResponse(200, "회원가입이 완료되었습니다.", null);
    }

    @GetMapping("/signup/email-duplication")
    public BaseResponse<CustomerDuplicateEmailResponseDto> duplicateEmail(@RequestBody CustomerDuplicateEmailRequestDto customerDuplicateEmailRequestDto) {
        boolean isDuplicate = customerService.duplicateEmail(customerDuplicateEmailRequestDto);
        if (isDuplicate) {
            return new BaseResponse<>(200, "이미 가입된 이메일입니다.", new CustomerDuplicateEmailResponseDto(isDuplicate));
        }
        return new BaseResponse<>(200, "사용가능한 이메일입니다.", new CustomerDuplicateEmailResponseDto(isDuplicate));
    }

}
