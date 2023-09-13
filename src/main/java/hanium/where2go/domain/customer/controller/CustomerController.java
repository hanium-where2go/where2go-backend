package hanium.where2go.domain.customer.controller;

import hanium.where2go.domain.customer.dto.*;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.service.CustomerService;
import hanium.where2go.domain.user.AuthUser;
import hanium.where2go.global.response.BaseErrorResponse;
import hanium.where2go.global.response.BaseResponse;
import hanium.where2go.global.smtp.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "Customer API")
public class CustomerController {

    private final CustomerService customerService;
    private final EmailService emailService;

    @Operation(summary = "회원가입", description = "회원가입 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "400", description = "아이디 중복", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signup(@RequestBody CustomerDto.SignupRequest signupRequestDto) {
        customerService.signup(signupRequestDto);
        emailService.sendAuthorizationEmail(signupRequestDto.getEmail());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "회원가입이 완료되었습니다.", null));
    }

    @Operation(summary = "OAuth2 인증", description = "OAuth2 인증 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OAuth2 인증 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        ))
    })
    @PostMapping("/oauth2/signup")
    public BaseResponse<String> oAuth2Signup(@AuthUser Customer customer, @RequestBody CustomerDto.SignupRequest signupRequestDto) {
        customerService.oAuth2Signup(customer, signupRequestDto);
        return new BaseResponse(200, "회원가입이 완료되었습니다.", null);
    }

    @Operation(summary = "이메일 중복 확인", description = "이메일 중복 확인 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이메일 중복 확인", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.DuplicateEmailResponse.class))}
        ))
    })
    @GetMapping("/signup/email-duplication")
    public BaseResponse<CustomerDto.DuplicateEmailResponse> duplicateEmail(@RequestBody CustomerDto.DuplicateEmailRequest duplicateEmailRequestDto) {
        boolean isDuplicate = customerService.duplicateEmail(duplicateEmailRequestDto);
        if (isDuplicate) {
            return new BaseResponse<>(200, "이미 가입된 이메일입니다.", new CustomerDto.DuplicateEmailResponse(isDuplicate));
        }
        return new BaseResponse<>(200, "사용가능한 이메일입니다.", new CustomerDto.DuplicateEmailResponse(isDuplicate));
    }

    @Operation(summary = "로그인", description = "로그인 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.LoginResponse.class))}
        )),
        @ApiResponse(responseCode = "404", description = "로그인 실패", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PostMapping("/login")
    public BaseResponse<CustomerDto.LoginResponse> login(@RequestBody CustomerDto.LoginRequest loginRequestDto) {
        return new BaseResponse<>(200, "로그인이 완료되었습니다.", customerService.login(loginRequestDto));
    }

    @Operation(summary = "이메일 찾기", description = "이메일 찾기 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이메일 찾기 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.FindEmailResponse.class))}
        )),
        @ApiResponse(responseCode = "404", description = "이메일 없음", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @GetMapping("/find-email")
    public BaseResponse<CustomerDto.FindEmailResponse> findEmail(@RequestBody CustomerDto.FindEmailRequest findEmailRequestDto) {
        return new BaseResponse<>(200, "사용자 아이디를 가져왔습니다.", customerService.findEmail(findEmailRequestDto));
    }

    @Operation(summary = "사용자 정보 가져오기", description = "사용자 정보 가져오기 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 정보 가져오기 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.InfoResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @GetMapping("/info")
    public ResponseEntity<BaseResponse<CustomerDto.InfoResponse>> getCustomerInfo(@AuthUser Customer customer) {
        CustomerDto.InfoResponse info = customerService.getInfo(customer);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 가져왔습니다.", info));
    }

    @Operation(summary = "사용자 정보 업데이트", description = "사용자 정보 업데이트 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 정보 업데이트 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.InfoResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PatchMapping("/info")
    public ResponseEntity<BaseResponse<CustomerDto.InfoResponse>> updateCustomerInfo(@AuthUser Customer customer, @RequestBody CustomerDto.InfoRequest infoRequest) {
        CustomerDto.InfoResponse info = customerService.updateInfo(customer, infoRequest);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 정보를 업데이트 했습니다.", info));
    }

    @Operation(summary = "이메일 인증", description = "이메일 인증 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이메일 인증 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을수 없음", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PatchMapping("/email-verification/{token}")
    public ResponseEntity<BaseResponse<String>> authorizeCustomer(@Parameter(name = "token", description = "이메일 인증 토큰") @PathVariable String token) {
        customerService.authorizeCustomer(token);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "이메일 인증을 성공했습니다.", null));
    }

    @Operation(summary = "사용자 포인트 불러오기", description = "사용자 포인트 불러오기 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 포인트 불러오기 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.PointResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @GetMapping("/point")
    public ResponseEntity<BaseResponse<CustomerDto.PointResponse>> getPoint(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 포인트를 가져왔습니다.", customerService.getPoint(customer)));
    }

    @Operation(summary = "사용자 선호 주종 업데이트", description = "사용자 선호 주종 업데이트 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 주종 업데이트 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PostMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<String>> postFavorLiquor(@AuthUser Customer customer, @RequestBody CustomerDto.FavorLiquorRequest favorLiquorRequestDto) {
        customerService.createFavorLiquor(customer, favorLiquorRequestDto.getLiquorId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 추가했습니다.", null));
    }

    @Operation(summary = "사용자 선호 주종 불러오기", description = "사용자 선호 주종 불러오기 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 주종 불러오기 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.FavorLiquorResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @GetMapping("/favor-liquors")
    public ResponseEntity<BaseResponse<CustomerDto.FavorLiquorResponse>> getFavorLiquors(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 가져왔습니다.", customerService.getFavorLiquors(customer)));
    }

    @Operation(summary = "사용자 선호 주종 삭제", description = "사용자 선호 주종 삭제 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 주종 삭제 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @DeleteMapping("/favor-liquors/{liquorId}")
    public ResponseEntity<BaseResponse<String>> deleteFavorLiquor(@AuthUser Customer customer, @Parameter(name = "liquorId", description = "주종 아이디") @PathVariable Long liquorId) {
        customerService.deleteFavorLiquor(customer, liquorId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 주종을 삭제했습니다.", null));
    }

    @Operation(summary = "사용자 선호 업종 업데이트", description = "사용자 선호 업종 업데이트 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 업종 업데이트 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PostMapping("/favor-categories")
    public ResponseEntity<BaseResponse<String>> postFavorCategory(@AuthUser Customer customer, @RequestBody CustomerDto.FavorCategoryRequest favorCategoryRequestDto) {
        customerService.createFavorCategory(customer, favorCategoryRequestDto.getCategoryId());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 추가했습니다.", null));
    }

    @Operation(summary = "사용자 선호 업종 불러오기", description = "사용자 선호 업종 불러오기 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 업종 불러오기 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.FavorCategoryResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @GetMapping("/favor-categories")
    public ResponseEntity<BaseResponse<CustomerDto.FavorCategoryResponse>> getFavorCategories(@AuthUser Customer customer) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 가져왔습니다.", customerService.getFavorCategories(customer)));
    }

    @Operation(summary = "사용자 선호 업종 삭제", description = "사용자 선호 업종 삭제 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 선호 업종 삭제 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @DeleteMapping("/favor-categories/{categoryId}")
    public ResponseEntity<BaseResponse<String>> deleteFavorCategory(@AuthUser Customer customer,@Parameter(name = "categoryId", description = "업종 ID") @PathVariable Long categoryId) {
        customerService.deleteFavorCategory(customer, categoryId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "사용자 선호 업종을 삭제했습니다.", null));
    }

    @Operation(summary = "사용자 탈퇴", description = "사용자 탈퇴 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @DeleteMapping("/withdrawal")
    public ResponseEntity<BaseResponse<String>> withdrawal(@AuthUser Customer customer) {
        customerService.withdrawal(customer);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "회원 탈퇴가 완료되었습니다.", null));
    }

    @Operation(summary = "사용자 비밀번호 변경", description = "사용자 비밀번호 변경 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 비밀번호 변경 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = String.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PatchMapping("/reset-password")
    public ResponseEntity<BaseResponse<String>> resetPassword(@AuthUser Customer customer, @RequestBody CustomerDto.ResetPasswordRequest resetPasswordRequestDto) {
        customerService.resetPassword(customer, resetPasswordRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "비밀번호가 변경되었습니다.", null));
    }

    @Operation(summary = "사용자 토큰 재발급", description = "사용자 토큰 재발급 API.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "사용자 토큰 재발급 성공", content = @Content(
            schemaProperties = {
                @SchemaProperty(name = "status", schema = @Schema(implementation = Integer.class)),
                @SchemaProperty(name = "message", schema = @Schema(implementation = String.class)),
                @SchemaProperty(name = "data", schema = @Schema(implementation = CustomerDto.LoginResponse.class))}
        )),
        @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class)))
    })
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<CustomerDto.LoginResponse>> reissue(HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new BaseResponse<>(HttpStatus.OK.value(), "토큰이 재발급 되었습니다.", customerService.reissue(request)));
    }

}
