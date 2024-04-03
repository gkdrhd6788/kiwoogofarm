package b303.farm.user.common.oauth2.userinfo;

import java.util.Map;

/**
 * 네이버의 경우에는, attributes를 받았을 때 바로 유저 정보가 있는 것이 아니라
 * 'response' Key로 한 번 감싸져있기 때문에, get("response")로 꺼낸 후
 * 사용할 정보 Key로 꺼내서 사용.
 *
 * 또한, get으로 꺼내면 Object로 반환되기 때문에 String으로 캐스팅하여 반환.
 *
 * json으로 받는 유저 정보에 따라 json의 키값으로 바꾸면 됨
 */

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if(response == null){
            return null;
        }

        return (String) response.get("id");
    }

    @Override
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if(response == null){
            return null;
        }

        return (String) response.get("nickname");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if(response == null){
            return null;
        }

        return (String) response.get("profile_image");
    }

    @Override
    public String getEmail() {
        return null;
    }
}
