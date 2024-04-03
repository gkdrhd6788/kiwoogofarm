package b303.farm.mypage;


import b303.farm.crop.entity.Crop;
import b303.farm.recipe.domain.Recipe;
import b303.farm.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRecipeRepository extends JpaRepository<RecipeFavorites,Long> {
    RecipeFavorites findByRecipeAndUser(Recipe recipe, User user);


//    RecipeFavorites findByRecipe(Recipe recipe);
    List<RecipeFavorites> findAllByUser(User user);

    List<RecipeFavorites> findAllByUserAndStatus(User user, boolean status);
    RecipeFavorites findByRecipeAndUserAndStatus(Recipe recipe, User user,boolean status);
}
