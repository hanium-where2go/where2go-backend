package hanium.where2go.domain.customer.controller;

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
}
