package hanium.where2go.domain.owner.controller;

import hanium.where2go.domain.owner.dto.OwnerDto;
import hanium.where2go.global.response.BaseResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
@RequestMapping("/owners")
public class OwnerController {

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity postOwners(@RequestBody OwnerDto.CreateRequest createOwnerDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse<>(HttpStatus.CREATED.value(), "회원가입이 완료되었습니다.", null));
    }

    // 이메일 중복 검사
    @GetMapping("/signup/email-duplication")
    public ResponseEntity duplicateEmail(@RequestBody OwnerDto.DuplicateEmailRequest duplicateEmailRequestDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이메일 중복 여부 검증이 완료되었습니다.", null));
    }

    // 이메일 인증
    @GetMapping("/email-verification/{token}")
    public ResponseEntity authorizeOwners(@PathVariable("token") String token){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "이메일 인증이 완료되었습니다.", null));
    }

    // 사업자 등록번호 인증
    @GetMapping("/business-verification")
    public ResponseEntity validateBusinessNum(@RequestParam String businessNum){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "사업자등록번호 조회가 완료되었습니다.", null));
    }

    // 사장님 회원 정보 수정
    @PatchMapping("/{owner-id}")
    public ResponseEntity patchOwner(@PathVariable("owner-id") @Min(1) Long ownerId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "회원정보 수정이 완료되었습니다.", null));
    }

    // 사장님 회원 정보 조회
    @GetMapping("/{owner-id}")
    public ResponseEntity getOwner(@PathVariable("owner-id") @Min(1) Long ownerId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "회원정보 조회가 완료되었습니다.", null));
    }

    // 사장님 회원 탈퇴
    @DeleteMapping("/{owner-id}")
    public ResponseEntity deleteOwners(@PathVariable("owner-id") @Min(1) Long ownerId){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "회원탈퇴가 완료되었습니다.", null));
    }

}

