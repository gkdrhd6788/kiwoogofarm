package b303.farm.garden.service;

import b303.farm.exception.GardenNotFoundException;
import b303.farm.garden.controller.GardenController;
import b303.farm.garden.entity.Garden;
import b303.farm.garden.repository.GardenRepository;
import b303.farm.mypage.FavoriteGardenRepository;
import b303.farm.mypage.GardenFavorites;
import b303.farm.user.User;
import b303.farm.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class GardenServiceImpl implements GardenService {
    private final GardenRepository gardenRepository;
    private final UserRepository userRepository;
    private final FavoriteGardenRepository favoriteGardenRepository;

    private Logger log = LoggerFactory.getLogger(GardenController.class);

    @Override
    public List<Garden> getAllGardens() {
        return gardenRepository.findAll();
    }
    @Override
    public Garden getGarden(Long id) {
        return gardenRepository.findById(id).orElseThrow(GardenNotFoundException::new);
    }


    @Override
    public String favoriteGarden(Long id, String email) {
        Garden garden = gardenRepository.findById(id).orElseThrow(GardenNotFoundException::new);
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        //db에서 garden,user가 둘다 같은 데이터가 있는지 조회한다(true,false불문)
        GardenFavorites gardenFavorites = favoriteGardenRepository.findByGardenAndUser(garden, currentUser);

        if (gardenFavorites == null) {
            gardenFavorites = GardenFavorites.createGardenFavorites(garden, currentUser);
            favoriteGardenRepository.save(gardenFavorites);
            return "텃밭 즐겨찾기 추가";
        } else {
            if (gardenFavorites.isStatus()) {
                gardenFavorites.unFavoriteGarden();
                log.info("텃밭 리스트 확인" + currentUser.getGardenFavoritesList());
                return "텃밭 즐겨찾기 취소";
            } else {
                gardenFavorites.reFavoriteGarden();
                return "텃밭 즐겨찾기 재설정";
            }
        }
    }

    @Override
    public List<Garden> getMyFavoriteGardens(String email) {
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<GardenFavorites> myFavoriteGardenList = favoriteGardenRepository.findAllByUserAndStatus(currentUser, true);
        log.info("텃밭 즐겨찾기리스트 확인" + currentUser.getGardenFavoritesList());
        List<Garden> myFavoriteGardens = new ArrayList<>();
        for (GardenFavorites gardenFavorites : myFavoriteGardenList) {
            myFavoriteGardens.add(gardenFavorites.getGarden());
        }
        return myFavoriteGardens;
    }

    @Override
    public Boolean checkGardenIsLiked(Long id, String email) {
        Garden garden = gardenRepository.findById(id).orElseThrow(GardenNotFoundException::new);
        User currentUser = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        GardenFavorites gardenFavorites = favoriteGardenRepository.findByGardenAndUserAndStatus(garden, currentUser, true);
        return gardenFavorites != null;
    }
}