package b303.farm.user;

import b303.farm.mypage.CropFavorites;
import b303.farm.mypage.GardenFavorites;
import b303.farm.mypage.RecipeFavorites;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    // auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="email")
    private String email;   // 이메일
    @Column(name="password")
    private String password;    // 비밀번호
    @Column(name="nickname")
    private String nickname;   // 닉네임
    @Column(name="image_url")
    private String imageUrl;    // 프로필 이미지

    @Column(name="age")
    private int age;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;  //카카오, 네이버, 구글

    @Column(name="social_id")
    private String socialId;    //로그인한 소셜 타입의 식별자 값

    @Column(name="refresh_token")
    private String refreshToken;    // 리프레시 토큰

    // 유저 권한 설정 메서드
    public void authorizeUser(){
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메서드
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }

    //추가한 부분 start
    @OneToMany(mappedBy = "user")
    private List<RecipeFavorites> recipeFavoritesList= new ArrayList<>();

    //==비즈니스로직 및 (User,RecipeFavorites)연관관계편의 메서드==//
    public void addRecipeFavorite(RecipeFavorites recipeFavorites) {
        recipeFavoritesList.add(recipeFavorites);
        recipeFavorites.setUser(this);
    }
    public void removeRecipeFavorite(RecipeFavorites recipeFavorites) {
        recipeFavoritesList.remove(recipeFavorites);
        //이 부분에 setUser(null)추가하면 안됨.findByRecipeAndUser이 부분에서 조회가 안됨.
    }
    //추가한 부분 end

    // 농작물 즐겨찾기 기능 관련
    @OneToMany(mappedBy = "user")
    private List<CropFavorites> cropFavoritesList= new ArrayList<>();

    //==비즈니스로직 및 (User,CropFavorites)연관관계편의 메서드==//
    public void addCropFavorite(CropFavorites cropFavorites) {
        cropFavoritesList.add(cropFavorites);
        cropFavorites.setUser(this);
    }
    public void removeCropFavorite(CropFavorites cropFavorites) {
        cropFavoritesList.remove(cropFavorites);
        //이 부분에 setUser(null)추가하면 안됨.findByCropAndUser이 부분에서 조회가 안됨.
    }

    //텃밭 즐겨찾기 관련 기능
    @OneToMany(mappedBy = "user")
    private List<GardenFavorites> gardenFavoritesList= new ArrayList<>();


    //==비즈니스로직 및 (User,GardenFavorites)연관관계편의 메서드==//
    public void addGardenFavorite(GardenFavorites gardenFavorites) {
        gardenFavoritesList.add(gardenFavorites);
        gardenFavorites.setUser(this);
    }
    public void removeGardenFavorite(GardenFavorites gardenFavorites) {
        gardenFavoritesList.remove(gardenFavorites);
        //이 부분에 setUser(null)추가하면 안됨.findByGardenAndUser이 부분에서 조회가 안됨.
    }

}
