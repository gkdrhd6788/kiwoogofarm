package b303.farm.crop.repository;

import b303.farm.crop.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByNameContaining(String name);

    // 카테고리로 농작물 검색
    List<Crop> findByCategory(String category);

    // 1. 초보자용: difficulty가 "쉬움"인 것들
    List<Crop> findByDifficulty(String difficulty);

    // 2. 빛이 적어도 되는: sunshine이 "하"인 것들
    List<Crop> findBySunshine(String sunshine);

    // 3. 물주기가 긴: water_period가 "상"인 것들
    List<Crop> findByWaterPeriod(String waterPeriod);

    // 4. 수경재배가 가능한: is_hydroponics가 TRUE인 것들
    List<Crop> findByIsHydroponics(int isHydroponics);
}
