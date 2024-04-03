package b303.farm.user.service;
import b303.farm.user.dto.UserInfoDTO;
import b303.farm.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import b303.farm.user.User;


@Service
public class MyPageService {

    private final UserRepository userRepository;

    @Autowired
    public MyPageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDTO getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new UserInfoDTO(user.getId(), user.getNickname(), user.getImageUrl());
    }
}

