package b303.farm.user.dto;

import lombok.Getter;

@Getter
public class UserInfoDTO {
    private final Long id;
    private final String nickname;
    private final String image_url;

    public UserInfoDTO(Long id, String nickname, String image_url) {
        this.id = id;
        this.nickname = nickname;
        this.image_url = image_url;
    }
}
