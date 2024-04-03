package b303.farm.recipe.controller;

import b303.farm.recipe.service.RecipeService;
import b303.farm.recipe.domain.Recipe;
import b303.farm.recipe.dto.RecipeDto;
import b303.farm.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    private Logger log = LoggerFactory.getLogger(RecipeController.class);

    @GetMapping("/all")
    public ResponseEntity<List<RecipeDto>> getRecipeList() {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        List<RecipeDto> recipeDtoList = new ArrayList<>();

        log.info("전체 레시피 정보");
        for(Recipe r : recipeList) {
            RecipeDto recipeDto = RecipeDto.builder()
                    .id(r.getId())
                    .serialNum(r.getSerialnum())
                    .name(r.getName())
                    .info(r.getInfo())
                    .ingredients(r.getIngredients())
                    .cook(r.getCook())
                    .imageUrl(r.getImage_url())
                    .difficulty(r.getDifficulty())
                    .likes(r.getLikes())
                    .cookTime(r.getCookTime())
                    .servings(r.getServings())
                    .build();
            log.info("레시피 정보: {}", recipeDto);
            recipeDtoList.add(recipeDto);
        }
        return ResponseEntity.ok().body(recipeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        RecipeDto recipeDto = RecipeDto.builder()
                .id(recipe.getId())
                .serialNum(recipe.getSerialnum())
                .name(recipe.getName())
                .info(recipe.getInfo())
                .ingredients(recipe.getIngredients())
                .cook(recipe.getCook())
                .imageUrl(recipe.getImage_url())
                .difficulty(recipe.getDifficulty())
                .likes(recipe.getLikes())
                .cookTime(recipe.getCookTime())
                .servings(recipe.getServings())
                .recipeDetailList(recipe.getRecipeDetailList())
//                .recipeDetailList(recipeService.getRecipeDetailList(recipe.getId()))
                .build();
        log.info("레시피 상세 정보: {}", recipeDto);
        return ResponseEntity.ok().body(recipeDto);
    }

    //즐겨찾기 설정/해제
    @PostMapping("/{id}/favorites/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> favoriteRecipe(@PathVariable final Long id,@PathVariable final String email) {
        String responseMessage = recipeService.favoriteRecipe(id,email);
        return ResponseEntity.ok(responseMessage);
    }

    //내가 찜한 레시피 조회
    @GetMapping("/myFavorites/{email}")
    public ResponseEntity<List<RecipeDto>> getMyRecipeFavorites(@PathVariable final String email) {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        List<Recipe> myFavoriteRecipes = recipeService.getMyFavoriteRecipes(email);

        log.info("유저가 즐겨찾기한 레시피 정보");
        for (Recipe r : myFavoriteRecipes) {
            RecipeDto recipeDto = RecipeDto.builder()
                    .id(r.getId())
                    .serialNum(r.getSerialnum())
                    .name(r.getName())
                    .info(r.getInfo())
                    .ingredients(r.getIngredients())
                    .cook(r.getCook())
                    .imageUrl(r.getImage_url())
                    .difficulty(r.getDifficulty())
                    .likes(r.getLikes())
                    .cookTime(r.getCookTime())
                    .servings(r.getServings())
                    .build();
            log.info("즐겨찾기한 레시피 정보: {}", recipeDto);
            recipeDtoList.add(recipeDto);
        }
        return ResponseEntity.ok().body(recipeDtoList);
    }
//지우지 마세요!!
//    @GetMapping("/search/{keyword}")
//    public ResponseEntity<List<RecipeDto>> getRecipeByKeyword(@PathVariable String keyword) {
//        List<Recipe> recipeList = recipeService.getRecipeListByKeyword(keyword);
//        List<RecipeDto> recipeDtoList = new ArrayList<>();
//
//        log.info("해당 제목을 포함한 레시피 정보");
//        for(Recipe r : recipeList) {
//            RecipeDto recipeDto = RecipeDto.builder()
//                    .id(r.getId())
//                    .serialNum(r.getSerialnum())
//                    .name(r.getName())
//                    .info(r.getInfo())
//                    .ingredients(r.getIngredients())
//                    .cook(r.getCook())
//                    .imageUrl(r.getImage_url())
//                    .difficulty(r.getDifficulty())
//                    .likes(r.getLikes())
//                    .cookTime(r.getCookTime())
//                    .servings(r.getServings())
//                    .build();
//            log.info("레시피 정보: {}", recipeDto);
//            recipeDtoList.add(recipeDto);
//        }
//        return ResponseEntity.ok().body(recipeDtoList);
//    }

    //수지 테스트
    @GetMapping("/searchByCropId/{cropId}")
    public ResponseEntity<List<RecipeDto>> getRecipeByCropId(@PathVariable Long cropId) {
        List<Recipe> recipeList = recipeService.getRecipeListByKeyword(cropId);
        List<RecipeDto> recipeDtoList = new ArrayList<>();

        log.info("해당 제목을 포함한 레시피 정보");
        for(Recipe r : recipeList) {
            RecipeDto recipeDto = RecipeDto.builder()
                    .id(r.getId())
                    .serialNum(r.getSerialnum())
                    .name(r.getName())
                    .info(r.getInfo())
                    .ingredients(r.getIngredients())
                    .cook(r.getCook())
                    .imageUrl(r.getImage_url())
                    .difficulty(r.getDifficulty())
                    .likes(r.getLikes())
                    .cookTime(r.getCookTime())
                    .servings(r.getServings())
                    .build();
            log.info("레시피 정보: {}", recipeDto);
            recipeDtoList.add(recipeDto);
        }
        return ResponseEntity.ok().body(recipeDtoList);
    }

    @GetMapping("/search/option")
    public ResponseEntity<List<RecipeDto>> getRecipeByIngredients(@RequestParam(value = "ingredients") List<String> ingredients, @RequestParam(value = "cook") String cook, @RequestParam(value = "difficulty") String difficulty) {
        List<Recipe> recipeList = recipeService.getRecipeListByOption(ingredients, cook, difficulty);
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        log.info("해당 옵션을 포함한 레시피 정보");
        for(Recipe r : recipeList) {
            RecipeDto recipeDto = RecipeDto.builder()
                    .id(r.getId())
                    .serialNum(r.getSerialnum())
                    .name(r.getName())
                    .info(r.getInfo())
                    .ingredients(r.getIngredients())
                    .cook(r.getCook())
                    .imageUrl(r.getImage_url())
                    .difficulty(r.getDifficulty())
                    .likes(r.getLikes())
                    .cookTime(r.getCookTime())
                    .servings(r.getServings())
                    .build();
            log.info("레시피 정보: {}", recipeDto);
            recipeDtoList.add(recipeDto);
        }
        return ResponseEntity.ok().body(recipeDtoList);
    }

    //해당 레시피가 로그인한 유저에게 좋아요 되어 있는지 여부
    @GetMapping("/checkRecipeIsLiked/{recipeId}/{email}")
    public Boolean IsRecipeLiked(@PathVariable final Long recipeId,@PathVariable final String email){
        return recipeService.checkRecipeIsLiked(recipeId,email);
    }
}
