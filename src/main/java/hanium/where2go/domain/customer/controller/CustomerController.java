package hanium.where2go.domain.customer.controller;

import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.service.CustomerService;
import hanium.where2go.global.response.BaseResponse;
import hanium.where2go.global.smtp.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    //private final EmailService emailService;

    @PostMapping("/signup")
    public BaseResponse<String> signup(@RequestBody CustomerSignupRequestDto customerSignupRequestDto) {
        customerService.signup(customerSignupRequestDto);
       // emailService.sendSimpleMessage(customerSignupRequestDto.getEmail());
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

    @PostMapping("/login")
    public BaseResponse<CustomerLoginResponseDto> login(@RequestBody CustomerLoginRequestDto customerLoginRequestDto) {
        return new BaseResponse<>(200, "로그인이 완료되었습니다.", customerService.login(customerLoginRequestDto));
    }

    @GetMapping("/find-email")
    public BaseResponse<CustomerFindEmailResponseDto> findEmail(@RequestBody CustomerFindEmailRequestDto customerFindEmailRequestDto) {
        return new BaseResponse<>(200, "사용자 아이디를 가져왔습니다.", customerService.findEmail(customerFindEmailRequestDto));
    }
}
