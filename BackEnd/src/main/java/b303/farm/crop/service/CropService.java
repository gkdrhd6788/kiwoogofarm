package b303.farm.crop.service;


import b303.farm.crop.entity.Crop;
import b303.farm.recipe.domain.Recipe;

import java.util.List;

public interface CropService {
    List<Crop> getAllCrops();
    Crop getCropDetails(Long id);
    List<Crop> searchCrops(String name);

    String favoriteCrop(Long id,String email);
    List<Crop> getMyFavoriteCrops(String email);

    // (필터링) 카테고리로 농작물 검색
    List<Crop> searchCropsByCategory(String category);

    // 필터링
    List<Crop> getEasyCrops();
    List<Crop> getCropsWithLowSunshine();
    List<Crop> getCropsWithHighWaterPeriod();
    List<Crop> getHydroponicsCrops();

    Boolean checkCropIsLiked(Long id, String email);

}
