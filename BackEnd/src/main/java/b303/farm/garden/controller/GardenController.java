package b303.farm.garden.controller;
import b303.farm.garden.dto.GardenDto;
import b303.farm.garden.entity.Garden;
import b303.farm.garden.service.GardenService;
import b303.farm.recipe.controller.RecipeController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/garden")
@RequiredArgsConstructor
public class GardenController {
    private final GardenService gardenService;

    private Logger log = LoggerFactory.getLogger(RecipeController.class);

    @GetMapping("/all")
    public ResponseEntity<?> getGardenList() {
        return ResponseEntity.ok().body(gardenService.getAllGardens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGarden(@PathVariable Long id) {
        return ResponseEntity.ok().body(gardenService.getGarden(id));
    }

    //내가 찜한 레시피 조회
    @GetMapping("/myGardenFavorites/{email}")
    public ResponseEntity<?> getMyFavoriteGardens(@PathVariable final String email) {
        List<GardenDto> gardenDtoList = new ArrayList<>();
        List<Garden> myFavoriteGardens = gardenService.getMyFavoriteGardens(email);

        log.info("유저가 즐겨찾기한 레시피 정보");
        for(Garden g : myFavoriteGardens) {
            GardenDto gardenDto = GardenDto.builder()
                    .id(g.getId())
                    .gardenOwner(g.getGardenOwner())
                    .gardenName(g.getGardenName())
                    .gardenAddress(g.getGardenAddress())
                    .gardenSize(g.getGardenSize())
                    .gardenExtra(g.getGardenExtra())
                    .gardenApply(g.getGardenApply())
                    .gardenLat(g.getGardenLat())
                    .gardenLong(g.getGardenLong())
                    .build();
            log.info("레시피 정보: {}", gardenDto);
            gardenDtoList.add(gardenDto);
        }
        return ResponseEntity.ok().body(gardenDtoList);
    }

    @PostMapping("/{id}/gardenfavorite/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> favoriteGarden(@PathVariable Long id, @PathVariable String email) {
        return ResponseEntity.ok().body(gardenService.favoriteGarden(id, email));
    }

    @GetMapping("/{id}/checkGardenIsLiked/{email}")
    public Boolean checkGardenIsLiked(@PathVariable Long id, @PathVariable String email) {
        return gardenService.checkGardenIsLiked(id, email);
    }
}
