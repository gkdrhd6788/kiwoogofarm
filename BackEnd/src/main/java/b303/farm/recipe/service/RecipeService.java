package b303.farm.recipe.service;

import b303.farm.recipe.domain.Recipe;
import b303.farm.recipe.domain.RecipeDetail;
import b303.farm.user.User;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();
    Recipe getRecipe(Long id);

    String favoriteRecipe(Long id,String email);

    List<Recipe> getMyFavoriteRecipes(String email);

    List<RecipeDetail> getRecipeDetailList(Long recipeId);
    List<Recipe> getRecipeListByKeyword(Long cropId);
//    List<Recipe> getRecipeListByOption(String ingredient, String cook, String difficulty);
    List<Recipe> getRecipeListByOption(List<String> ingredient, String cook, String difficulty);

    Boolean checkRecipeIsLiked(Long recipeId,String email);
}
