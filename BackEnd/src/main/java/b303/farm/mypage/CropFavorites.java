package b303.farm.mypage;

import b303.farm.crop.entity.Crop;
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
@Table(name = "cropfavorites")
public class CropFavorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="crop_favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="crop_id")
    private Crop crop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "is_liked", nullable = false)
    private boolean status ; //erd랑 변수명 다름에 주의. true= 즐겨찾기, false= 즐겨찾기 취소

    //생성 메서드
    public static CropFavorites createCropFavorites(Crop crop, User user){
        CropFavorites cropFavorites = new CropFavorites();
        cropFavorites.setCrop(crop);
        cropFavorites.setStatus(true);
        user.addCropFavorite(cropFavorites);
        crop.addLike();
        return cropFavorites;
    }

    public void unFavoriteCrop(){
        //favoriteCrop의 status를 변경하고
        setStatus(false);
        //User와 CropFavorite연관관계끊어버리고(단방향으로)
        getUser().removeCropFavorite(this);  //여기가 문제
        //Recipe에 있는 likes++
        getCrop().removeLike();
    }


    public void reFavoriteCrop(){
        //여기는 연관관계생성하면 안됨(즐겨찾기 재설정하는데 새로운 필드가 생겨서)
        setStatus(true);
        //==============설정해줘야 되나??????===============
        getUser().addCropFavorite(this);
        getCrop().addLike();
    }
}
