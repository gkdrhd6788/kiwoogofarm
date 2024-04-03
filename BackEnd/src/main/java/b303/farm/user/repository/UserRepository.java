package b303.farm.user.repository;


import b303.farm.user.SocialType;
import b303.farm.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    @Query("SELECT u.id as id, u.nickname as nickname FROM User u WHERE u.email = ?1")
    Optional<User> findUserInfoByEmail(String email);
}
