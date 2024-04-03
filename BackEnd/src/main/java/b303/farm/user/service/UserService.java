package b303.farm.user.service;

import b303.farm.user.Role;
import b303.farm.user.User;
import b303.farm.user.repository.UserRepository;
import b303.farm.user.dto.UserSignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
//    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDTO userSignUpDTO) throws Exception{
        if(userRepository.findByEmail(userSignUpDTO.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if(userRepository.findByNickname(userSignUpDTO.getNickname()).isPresent()){
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDTO.getEmail())
                .password(userSignUpDTO.getPassword())
                .nickname(userSignUpDTO.getNickname())
                .age(userSignUpDTO.getAge())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
    }
}
