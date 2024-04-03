package b303.farm.mypage;

import b303.farm.recipe.domain.Recipe;

import b303.farm.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recipefavorites")
public class RecipeFavorites {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="recipe_favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "is_liked", nullable = false)
    private boolean status ; //erd랑 변수명 다름에 주의. true= 즐겨찾기, false= 즐겨찾기 취소

    //생성 메서드
    public static RecipeFavorites createRecipeFavorites(Recipe recipe, User user){
        RecipeFavorites recipeFavorites = new RecipeFavorites();
        recipeFavorites.setRecipe(recipe);
        recipeFavorites.setStatus(true);
        user.addRecipeFavorite(recipeFavorites);
        recipe.addLike();
        return recipeFavorites;
    }

    public void unFavoriteRecipe(){
        //favoriteRecipe의 status를 변경하고
        setStatus(false);
        //User와 RecipeFavorite연관관계끊어버리고(단방향으로)
        getUser().removeRecipeFavorite(this);  //여기가 문제
        //Recipe에 있는 likes++
        getRecipe().removeLike();
    }


    public void reFavoriteRecipe(){
        //여기는 연관관계생성하면 안됨(즐겨찾기 재설정하는데 새로운 필드가 생겨서)
        setStatus(true);
        //==============설정해줘야 되나??????===============
        getUser().addRecipeFavorite(this);
        getRecipe().addLike();
    }
}
