package b303.farm.recipe.dto;

import b303.farm.recipe.domain.RecipeDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecipeDto {

    private Long id;
    private Long serialNum;
    private String name;
    private String info;
    private String ingredients;
    private String cook;
    private String imageUrl;
    private String difficulty;
    private int likes;
    private String servings;
    private String cookTime;
    private List<RecipeDetail> recipeDetailList;

    @Builder
    public RecipeDto(Long id, Long serialNum, String name, String info, String ingredients, String cook, String imageUrl, String difficulty, int likes, String servings, String cookTime, List<RecipeDetail> recipeDetailList) {
        this.id = id;
        this.serialNum = serialNum;
        this.name = name;
        this.info = info;
        this.ingredients = ingredients;
        this.cook = cook;
        this.imageUrl = imageUrl;
        this.difficulty = difficulty;
        this.likes = likes;
        this.servings = servings;
        this.cookTime = cookTime;
        this.recipeDetailList = recipeDetailList;
    }
}
