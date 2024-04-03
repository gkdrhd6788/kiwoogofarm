//package com.ssafy.social2.repository;
//
//import b303.farm.user.SocialType;
//import b303.farm.user.User;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.Optional;
//
//@Mapper
//public interface UserMapper {
//    Optional<User> findByEmail(String email);
//
//    Optional<User> findByNickname(String nickname);
//
//    Optional<User> findByRefreshToken(String refresToken);
//
//    Optional<User> findBySocialTypeAndSocialId(@Param("type") SocialType socialType, @Param("socialId") String socialId);
//
//    void save(User user);
//
//    void saveToken(User user);
//}
//
