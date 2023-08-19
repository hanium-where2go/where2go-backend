package hanium.where2go.domain.liquor.controller;

import hanium.where2go.domain.liquor.dto.LiquorDto;
import hanium.where2go.domain.liquor.service.LiquorService;
import hanium.where2go.global.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquors")
public class LiquorController {

    private final LiquorService liquorService;

    @PostMapping
    public ResponseEntity<BaseResponse> postLiquor(@Valid @RequestBody LiquorDto liquorDto) {
        liquorService.createLiquorService(liquorDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse(HttpStatus.CREATED.value(), "주종이 생성되었습니다.", null));
    }

    @PatchMapping("/{liquor-id}")
    public ResponseEntity<BaseResponse> patchLiquor(@PathVariable("liquor-id") @Min(1) Long liquorId,
                                                    @Valid @RequestBody LiquorDto liquorDto) {
        liquorService.updateLiquor(liquorDto, liquorId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "주종이 수정되었습니다.", null));
    }

    @GetMapping("/{liquor-id}")
    public ResponseEntity<BaseResponse> getLiquor(@PathVariable("liquor-id") @Min(1) Long liquorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(), "주종을 불러왔습니다.", liquorService.getLiquorById(liquorId)));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getLiquors() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(), "주종 목록을 불러왔습니다.", liquorService.getLiquors()));
    }

    @DeleteMapping("/{liquor-id}")
    public ResponseEntity<BaseResponse> deleteLiquor(@PathVariable("liquor-id") @Min(1) Long liquorId) {
        liquorService.deleteLiquor(liquorId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new BaseResponse(HttpStatus.NO_CONTENT.value(), "주종을 삭제했습니다.", null));
    }
}
