package b303.farm.recipe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "recipedetail")
public class RecipeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="recipedetail_id")
    private Long id;

    private int recipe_order;
    private String info;
    private String image_url;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;

    @Builder
    public RecipeDetail(Long id, int recipe_order, String info, String image_url, Recipe recipe) {
        this.id = id;
        this.recipe_order = recipe_order;
        this.info = info;
        this.image_url = image_url;
        this.recipe = recipe;
    }
}

