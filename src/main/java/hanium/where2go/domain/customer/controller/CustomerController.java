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

    @GetMapping("/info")
    public ResponseEntity<BaseResponse<CustomerInfoResponseDto>> getCustomerInfo(@AuthUser Customer customer) {
        CustomerInfoResponseDto info = customerService.getInfo(customer);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 가져왔습니다.", info));
    }

    @PatchMapping("/info")
    public ResponseEntity<BaseResponse<CustomerInfoResponseDto>> updateCustomerInfo(@AuthUser Customer customer, @RequestBody UserInfoRequestDto userInfoRequestDto) {
        CustomerInfoResponseDto info = customerService.updateInfo(customer, userInfoRequestDto);
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

    @PostMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<String>> postFavorLiquor(@AuthUser Customer customer, @RequestBody CustomerFavorLiquorRequestDto customerFavorLiquorRequestDto) {
        customerService.createFavorLiquor(customer, customerFavorLiquorRequestDto.getLiquorId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 추가했습니다.", null));
    }

    @GetMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<CustomerFavorLiquorResponseDto>> getFavorLiquors(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 가져왔습니다.", customerService.getFavorLiquors(customer)));
    }

    @DeleteMapping("/favor-liquors/{liquorId}")
    public ResponseEntity<BaseResponse<String>> deleteFavorLiquor(@AuthUser Customer customer, @PathVariable Long liquorId) {
        customerService.deleteFavorLiquor(customer, liquorId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 삭제했습니다.", null));
    }

    @PostMapping("/favor-categories")
    public ResponseEntity<BaseResponse<String>> postFavorCategory(@AuthUser Customer customer, @RequestBody CustomerFavorCategoryRequestDto customerFavorCategoryRequestDto) {
        customerService.createFavorCategory(customer, customerFavorCategoryRequestDto.getCategoryId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 추가했습니다.", null));
    }

    @GetMapping("/favor-categories")
    public ResponseEntity<BaseResponse<CustomerFavorLiquorResponseDto>> getFavorCategories(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 가져왔습니다.", customerService.getFavorCategories(customer)));
    }

    @DeleteMapping("/favor-categories/{categoryId}")
    public ResponseEntity<BaseResponse<String>> deleteFavorCategory(@AuthUser Customer customer, @PathVariable Long categoryId) {
        customerService.deleteFavorCategory(customer, categoryId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 삭제했습니다.", null));
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<BaseResponse<String>> withdrawal(@AuthUser Customer customer) {
        customerService.withdrawal(customer);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "회원 탈퇴가 완료되었습니다.", null));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<BaseResponse<String>> resetPassword(@AuthUser Customer customer, @RequestBody CustomerResetPasswordRequestDto customerResetPasswordRequestDto) {
        customerService.resetPassword(customer, customerResetPasswordRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "비밀번호가 변경되었습니다.", null));
    }

}
