package b303.farm.recipe.service;

import b303.farm.crop.entity.Crop;
import b303.farm.crop.repository.CropRepository;
import b303.farm.recipe.domain.RecipeDetailRepository;
import b303.farm.exception.RecipeNotFoundException;
import b303.farm.user.User;
import b303.farm.user.repository.UserRepository;
import b303.farm.mypage.FavoriteRecipeRepository;
import b303.farm.mypage.RecipeFavorites;
import b303.farm.recipe.controller.RecipeController;
import b303.farm.recipe.domain.Recipe;
import b303.farm.recipe.domain.RecipeRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import b303.farm.recipe.domain.*;
import org.springframework.data.jpa.domain.Specification;
@Transactional
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDetailRepository recipeDetailRepository;
    private final UserRepository userRepository;
    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private final CropRepository cropRepository;
    private Logger log = LoggerFactory.getLogger(RecipeController.class);
    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAllByOrderByLikesDesc();
    }

    @Override
    public Recipe getRecipe(Long id) {
//        List<RecipeDetail> recipeDetailList = recipeDetailRepository.findAllByRecipeId(id);
//        Recipe recipe = recipeRepository.findById(id).orElse(null).toBuilder().recipeDetailList(recipeDetailList).build();
////        recipe = Recipe.builder().recipeDetailList(recipeDetailList).build();
////        recipe = recipe.toBuilder().recipeDetailList(recipeDetailList).build();
//        return recipe;
        return recipeRepository.findById(id).orElse(null).toBuilder().recipeDetailList(recipeDetailRepository.findAllByRecipeId(id)).build();
    }

    @Override
//    @Transactional  //class단위로 넣어야 되는지?
    public String favoriteRecipe(Long id,String email){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        //db에서 recipe,user가 둘다 같은 데이터가 있는지 조회한다(true,false불문)
        RecipeFavorites recipeFavorites = favoriteRecipeRepository.findByRecipeAndUser(recipe, currentUser);
        //만약 없다면, 생성하고 저장한다.
        if(recipeFavorites == null){
            recipeFavorites = RecipeFavorites.createRecipeFavorites(recipe, currentUser);
            favoriteRecipeRepository.save(recipeFavorites);
            return "즐겨찾기 신규설정";  //다른 값?
        } else {
            //만약 존재한다면
            //그리고 status값이 true라면, status를 false로 바꾸고,연관관계도 끊고,좋아요수도 증감시킨다.
            if (recipeFavorites.isStatus()){
                recipeFavorites.unFavoriteRecipe();
//                favoriteRecipeRepository.save(recipeFavorites);

                log.info("리스트 확인 " + currentUser.getRecipeFavoritesList());//.get(0).getId()
                return "즐겨찾기 취소";
            } else {
                //status값이 false라면,status를 true로 바꾸고, 연관관계도 만들고, 좋아요 수도 증가시킨다.
                recipeFavorites.reFavoriteRecipe();
//                favoriteRecipeRepository.save(recipeFavorites);
                log.info("리스트 확인" + currentUser.getRecipeFavoritesList());//.get(0).getId()
                return "즐겨찾기 재설정";
            }
        }
    }

    @Override
//    @Transactional
    public List<Recipe> getMyFavoriteRecipes(String email) {
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<RecipeFavorites> myFavoriteRecipeList = favoriteRecipeRepository.findAllByUserAndStatus(currentUser, true);
        log.info("즐겨찾기리스트 확인" + currentUser.getRecipeFavoritesList());//.get(0).getId()
        List<Recipe> myFavoriteRecipes = new ArrayList<>();
        for (RecipeFavorites recipeFavorites : myFavoriteRecipeList) {
            myFavoriteRecipes.add(recipeFavorites.getRecipe());
        }
        return myFavoriteRecipes;
    }

    @Override
    public List<RecipeDetail> getRecipeDetailList(Long recipeId) {
        return recipeDetailRepository.findAllByRecipeId(recipeId);
    }

    public List<Recipe> getRecipeListByKeyword(Long cropId) {
        Optional<Crop> cropOptional = cropRepository.findById(cropId);
        if (cropOptional.isPresent()) {
            Crop crop = cropOptional.get();
            log.info("crop 확인 " + crop.getName());
            return recipeRepository.findAllByNameContaining(crop.getName());
        } else {
            return Collections.emptyList();
        }
    }

    public List<Recipe> getRecipeListByOption(List<String> ingredients, String cook, String difficulty) {
        Specification<Recipe> spec = (root, query, criteriaBuilder) -> null;
        for(String ingredient : ingredients) {
            spec = spec.and(RecipeSpecification.containingIngredients(ingredient));
        }
        if(cook!=null) spec = spec.and(RecipeSpecification.containingCook(cook));
        if(difficulty!=null) spec = spec.and(RecipeSpecification.containingDifficulty(difficulty));
        spec = spec.and(RecipeSpecification.orderByLikesDescending());
        return recipeRepository.findAll(spec);
    }

    @Override
    public Boolean checkRecipeIsLiked(Long recipeId,String email) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        RecipeFavorites recipeFavorite  = favoriteRecipeRepository.findByRecipeAndUserAndStatus(recipe,currentUser,true);
        return recipeFavorite!=null;
    }

}
