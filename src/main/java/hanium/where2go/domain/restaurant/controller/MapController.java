package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.service.MapService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/address")
    public ResponseEntity getAddressByKeywords(@RequestParam(value = "keywords", required = false) String keywords,
                                               @RequestParam(value = "longitude", required = false) String longitude,
                                               @RequestParam(value = "latitude",required = false) String latitude) {
        // 1. keywords로 주소를 검색할 경우
        if(keywords != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new BaseResponse<>(HttpStatus.OK.value(),"주소 데이터를 불러왔습니다.", mapService.getAddrByKeywords(keywords)));
        }

        // 2. coordinates로 주소를 검색할 경우
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(),"주소 데이터를 불러왔습니다.", mapService.getAddrByCoords(longitude, latitude)));
    }
}
