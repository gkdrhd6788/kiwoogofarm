package b303.farm.mypage;

import b303.farm.garden.entity.Garden;
import b303.farm.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "gardenfavorites")
public class GardenFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="garden_id")
    private Garden garden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "is_liked", nullable = false)
    private boolean status ; //erd랑 변수명 다름에 주의. true= 즐겨찾기, false= 즐겨찾기 취소

    //생성 메서드
    public static GardenFavorites createGardenFavorites(Garden garden, User user){
        GardenFavorites gardenFavorites = new GardenFavorites();
        gardenFavorites.setGarden(garden);
        gardenFavorites.setStatus(true);
        user.addGardenFavorite(gardenFavorites);
        return gardenFavorites;
    }

    public void unFavoriteGarden(){
        setStatus(false);
        getUser().removeGardenFavorite(this);
    }

    public void reFavoriteGarden(){
        setStatus(true);
        getUser().addGardenFavorite(this);
    }
}



