package b303.farm.mypage;

import b303.farm.garden.entity.Garden;
import b303.farm.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FavoriteGardenRepository extends JpaRepository<GardenFavorites,Long> {
    GardenFavorites findByGardenAndUser(Garden garden, User user);

    List<GardenFavorites> findAllByUserAndStatus(User user, boolean status);

    GardenFavorites findByGardenAndUserAndStatus(Garden garden, User user,boolean status);
}
