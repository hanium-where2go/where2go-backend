package hanium.where2go.domain.customer.controller;

import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.service.CustomerService;
import hanium.where2go.domain.user.AuthUser;
import hanium.where2go.global.response.BaseResponse;
import hanium.where2go.global.smtp.EmailService;
import jakarta.servlet.http.HttpServletRequest;
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
    public BaseResponse<String> signup(@RequestBody CustomerDto.SignupRequest signupRequestDto) {
        customerService.signup(signupRequestDto);
        emailService.sendAuthorizationEmail(signupRequestDto.getEmail());
        return new BaseResponse(200, "회원가입이 완료되었습니다.", null);
    }

    @PostMapping("/oauth2/signup")
    public BaseResponse<String> oAuth2Signup(@AuthUser Customer customer, @RequestBody CustomerDto.SignupRequest signupRequestDto) {
        customerService.oAuth2Signup(customer, signupRequestDto);
        return new BaseResponse(200, "회원가입이 완료되었습니다.", null);
    }

    @GetMapping("/signup/email-duplication")
    public BaseResponse<CustomerDto.DuplicateEmailResponse> duplicateEmail(@RequestBody CustomerDto.DuplicateEmailRequest duplicateEmailRequestDto) {
        boolean isDuplicate = customerService.duplicateEmail(duplicateEmailRequestDto);
        if (isDuplicate) {
            return new BaseResponse<>(200, "이미 가입된 이메일입니다.", new CustomerDto.DuplicateEmailResponse(isDuplicate));
        }
        return new BaseResponse<>(200, "사용가능한 이메일입니다.", new CustomerDto.DuplicateEmailResponse(isDuplicate));
    }

    @PostMapping("/login")
    public BaseResponse<CustomerDto.LoginResponse> login(@RequestBody CustomerDto.LoginRequest loginRequestDto) {
        return new BaseResponse<>(200, "로그인이 완료되었습니다.", customerService.login(loginRequestDto));
    }

    @GetMapping("/find-email")
    public BaseResponse<CustomerDto.FindEmailResponse> findEmail(@RequestBody CustomerDto.FindEmailRequest findEmailRequestDto) {
        return new BaseResponse<>(200, "사용자 아이디를 가져왔습니다.", customerService.findEmail(findEmailRequestDto));
    }

    @GetMapping("/info")
    public ResponseEntity<BaseResponse<CustomerDto.InfoResponse>> getCustomerInfo(@AuthUser Customer customer) {
        CustomerDto.InfoResponse info = customerService.getInfo(customer);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 가져왔습니다.", info));
    }

    @PatchMapping("/info")
    public ResponseEntity<BaseResponse<CustomerDto.InfoResponse>> updateCustomerInfo(@AuthUser Customer customer, @RequestBody CustomerDto.InfoRequest infoRequest) {
        CustomerDto.InfoResponse info = customerService.updateInfo(customer, infoRequest);
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
    public ResponseEntity<BaseResponse<CustomerDto.PointResponse>> getPoint(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 포인트를 가져왔습니다.", customerService.getPoint(customer)));
    }

    @PostMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<String>> postFavorLiquor(@AuthUser Customer customer, @RequestBody CustomerDto.FavorLiquorRequest favorLiquorRequestDto) {
        customerService.createFavorLiquor(customer, favorLiquorRequestDto.getLiquorId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 추가했습니다.", null));
    }

    @GetMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<CustomerDto.FavorLiquorResponse>> getFavorLiquors(@AuthUser Customer customer) {
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
    public ResponseEntity<BaseResponse<String>> postFavorCategory(@AuthUser Customer customer, @RequestBody CustomerDto.FavorCategoryRequest favorCategoryRequestDto) {
        customerService.createFavorCategory(customer, favorCategoryRequestDto.getCategoryId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 추가했습니다.", null));
    }

    @GetMapping("/favor-categories")
    public ResponseEntity<BaseResponse<CustomerDto.FavorCategoryResponse>> getFavorCategories(@AuthUser Customer customer) {
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
    public ResponseEntity<BaseResponse<String>> resetPassword(@AuthUser Customer customer, @RequestBody CustomerDto.ResetPasswordRequest resetPasswordRequestDto) {
        customerService.resetPassword(customer, resetPasswordRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "비밀번호가 변경되었습니다.", null));
    }

    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<CustomerDto.LoginResponse>> reissue(HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "토큰이 재발급 되었습니다.", customerService.reissue(request)));
    }

}
