package hanium.where2go.domain.customer.controller;

import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.service.CustomerService;
import hanium.where2go.domain.user.AuthUser;
import hanium.where2go.domain.user.dto.UserInfoRequestDto;
import hanium.where2go.global.response.BaseResponse;
import hanium.where2go.global.smtp.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public BaseResponse<String> signup(@RequestBody CustomerSignupRequestDto customerSignupRequestDto) {
        customerService.signup(customerSignupRequestDto);
        emailService.sendAuthorizationEmail(customerSignupRequestDto.getEmail());
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

    @GetMapping("/{customerId}/info")
    public ResponseEntity<BaseResponse<CustomerInfoResponseDto>> getCustomerInfo(@AuthUser Customer customer, @PathVariable Long customerId) {
        CustomerInfoResponseDto info = customerService.getInfo(customer, customerId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 가져왔습니다.", info));
    }

    @PatchMapping("/{customerId}/info")
    public ResponseEntity<BaseResponse<CustomerInfoResponseDto>> updateCustomerInfo(@AuthUser Customer customer, @PathVariable Long customerId, @RequestBody UserInfoRequestDto userInfoRequestDto) {
        CustomerInfoResponseDto info = customerService.updateInfo(customer, customerId, userInfoRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 업데이트 했습니다.", info));
    }

    @PatchMapping("/email-verification/{token}")
    public ResponseEntity<BaseResponse<String>> authorizeCustomer(@PathVariable String token) {
        customerService.authorizeCustomer(token);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 업데이트 했습니다.", null));
    }

    @GetMapping("/point")
    public ResponseEntity<BaseResponse<CustomerPointResponseDto>> getPoint(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 포인트를 가져왔습니다.", customerService.getPoint(customer)));
    }
}
