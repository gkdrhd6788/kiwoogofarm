package b303.farm.recipe.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="recipe_id")
    private Long id;

    private Long serialnum;
    private String name;
    private String info;
    private String ingredients;
    private String cook;
    private String image_url;
    private String difficulty;
    private int likes;   //찜_만개의 레시피(default)+우리사이트즐겨찾기
    private String servings;
    private String cookTime;

    @OneToMany(mappedBy = "recipe")
    @JsonManagedReference
    @OrderBy("recipe_order")
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();


    //==비즈니스 로직==// 데이터가 있는 엔티티에서 직접 관리하는 게 좋다.
    /**likes증가(favorite)**/
    public void addLike(){
        this.likes++;
    }
    /**likes감소(favorite)**/
    public void removeLike(){
        if (this.likes > 0) {
            this.likes--;
        }
    }

    @Builder(toBuilder = true)
    public Recipe(Long id, Long serialnum, String name, String info, String ingredients, String cook, String image_url, String difficulty, int likes, String servings, String cookTime, List<RecipeDetail> recipeDetailList) {
        this.id = id;
        this.serialnum = serialnum;
        this.name = name;
        this.info = info;
        this.ingredients = ingredients;
        this.cook = cook;
        this.image_url = image_url;
        this.difficulty = difficulty;
        this.likes = likes;
        this.servings = servings;
        this.cookTime = cookTime;
        this.recipeDetailList = recipeDetailList;
    }
}

