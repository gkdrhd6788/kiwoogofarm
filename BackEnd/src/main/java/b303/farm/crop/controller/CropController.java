package b303.farm.crop.controller;

import b303.farm.crop.dto.CropDto;
import b303.farm.crop.entity.Crop;
import b303.farm.crop.service.CropService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/crop")
@RequiredArgsConstructor
public class CropController {

    private static final Logger log = LoggerFactory.getLogger(CropController.class);

    private final CropService cropService;

    // 농작물 전체 조회
    @GetMapping("/all")
    public List<Crop> getAllCrops() {
        return cropService.getAllCrops();
    }

    // 농작물 상세 조회
    @GetMapping("/{id}")
    public Crop getCropDetails(@PathVariable("id") Long id) {
        return cropService.getCropDetails(id);
    }

    //검색
    @GetMapping("/search")
    public List<Crop> searchCrops(@RequestParam(name = "name") String name) {
        return cropService.searchCrops(name);
    }

    // (필터링)카테고리로 농작물 검색
    @GetMapping("/searchByCategory")
    public ResponseEntity<List<Crop>> searchCropsByCategory(@RequestParam(name = "category") String category) {
        List<Crop> crops = cropService.searchCropsByCategory(category);
        if (crops.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(crops, HttpStatus.OK);
    }

    //(필터링)
    // 초보자용 농작물 조회
    @GetMapping("/easy")
    public List<Crop> getEasyCrops() {
        return cropService.getEasyCrops();
    }

    // 빛이 적어도 되는 농작물 조회
    @GetMapping("/lowSunshine")
    public List<Crop> getCropsWithLowSunshine() {
        return cropService.getCropsWithLowSunshine();
    }

    // 물주기가 긴 농작물 조회
    @GetMapping("/highWaterPeriod")
    public List<Crop> getCropsWithHighWaterPeriod() {
        return cropService.getCropsWithHighWaterPeriod();
    }

    // 수경재배가 가능한 농작물 조회
    @GetMapping("/hydroponics")
    public List<Crop> getHydroponicsCrops() {
        return cropService.getHydroponicsCrops();
    }

    //즐겨찾기 설정/해제
    @PostMapping("/{id}/favorites/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> favoriteCrop(@PathVariable("id") final Long id, @PathVariable("email") final String email) {
        String responseMessage = cropService.favoriteCrop(id, email);
        return ResponseEntity.ok(responseMessage);
    }

    //내가 찜한 농작물 조회
    @GetMapping("/myFavorites/{email}")
    public ResponseEntity<List<CropDto>> getMyCropFavorites(@PathVariable("email") final String email) {
        List<CropDto> cropDtoList = new ArrayList<>();
        List<Crop> myFavoriteCrops = cropService.getMyFavoriteCrops(email);

        log.info("유저가 즐겨찾기한 농작물 정보");
        for (Crop c : myFavoriteCrops) {
            CropDto cropDto = CropDto.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .thumbnailUrl(c.getThumbnailUrl())
                    .imageUrl(c.getImageUrl())
                    .category(c.getCategory())
                    .temperature(c.getTemperature())
                    .difficulty(c.getDifficulty())
                    .sunshine(c.getSunshine())
                    .growTime(c.getGrowTime())
                    .height(c.getHeight())
                    .waterPeriod(c.getWaterPeriod())
                    .humidity(c.getHumidity())
//                    .isHydroponics(c.getIsHydroponics())
                    .growStart(c.getGrowStart())
                    .waterExit(c.getWaterExit())
                    .cropInfo(c.getCropInfo())
                    .waterPeriodInfo(c.getWaterPeriodInfo())
                    .sunshineInfo(c.getSunshineInfo())
                    .humidityInfo(c.getHumidityInfo())
                    .temperatureInfo(c.getTemperatureInfo())
                    .effectInfo(c.getEffectInfo())
                    .link(c.getLink())
                    .growInfo(c.getGrowInfo())
                    .likes(c.getLikes())
                    .build();
            log.info("즐겨찾기한 농작물 정보: {}", cropDto);
            cropDtoList.add(cropDto);
        }
        return ResponseEntity.ok().body(cropDtoList);
    }

    //해당 농작물이 로그인한 유저에게 좋아요 되어 있는지 여부
    @GetMapping("/checkCropIsLiked/{id}/{email}")
    public Boolean IsCropLiked(@PathVariable("id") final Long id,@PathVariable("email") final String email){
        return cropService.checkCropIsLiked(id,email);
    }
}
