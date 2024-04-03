package b303.farm.garden.service;

import b303.farm.garden.entity.Garden;

import java.util.List;

public interface GardenService {
    List<Garden> getAllGardens();

    Garden getGarden(Long id);

    String favoriteGarden(Long id, String email);

    List<Garden> getMyFavoriteGardens(String email);

    Boolean checkGardenIsLiked(Long id,String email);
}
