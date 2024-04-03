package b303.farm.user.common.oauth2.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname() {
        return (String) isVaildProfile().get("nickname");
    }

    @Override
    public String getImageUrl() {
        return (String) isVaildProfile().get("thumbnail_image_url");
    }

    @Override
    public String getEmail() {
        return (String) isVaildAttribute().get("email");
    }

    public Map<String, Object> isVaildAttribute() {
        if (attributes != null) {
            return (Map<String, Object>) attributes.get("kakao_account");
        }
        return null;
    }

    public Map<String, Object> isVaildProfile() {
        Map<String, Object> check = isVaildAttribute();
        if (check != null) {
            return (Map<String, Object>) check.get("profile");
        }
        return null;
    }
}
