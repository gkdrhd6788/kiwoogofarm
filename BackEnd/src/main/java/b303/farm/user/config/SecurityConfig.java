package b303.farm.user.config;

import b303.farm.user.common.jwt.filter.JwtAuthenticationProcessingFilter;
import b303.farm.user.common.jwt.service.JwtService;
import b303.farm.user.common.login.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import b303.farm.user.common.oauth2.handler.OAuth2LoginFailureHandler;
import b303.farm.user.common.oauth2.handler.OAuth2LoginSuccessHandler;
import b303.farm.user.common.oauth2.service.CustomOAuth2UserService;
import b303.farm.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import b303.farm.user.common.login.handler.LoginFailureHandler;
import b303.farm.user.common.login.handler.LoginSuccessHandler;
import b303.farm.user.common.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .formLogin(AbstractHttpConfigurer::disable) // FormLogin 사용 X
                .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 X
                .csrf(AbstractHttpConfigurer::disable)  // csrf 보안 사용 X
//                .headers((header) -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /* URL별 권한 관리 옵션 */
                .authorizeHttpRequests((authorize) ->
                        authorize
                                // 아이콘, css, js 관련
                                // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
//                                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()   // 토큰 발급 경로 허용              
                                .requestMatchers("/login").permitAll()    // 회원가입 접근 가능
                                .requestMatchers("/crop/*").permitAll()
                                .anyRequest().permitAll()   // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능    
                )

                /* 소셜 로그인 설정 */
                .oauth2Login((oauth) -> oauth
                                //OAuth2 로그인시 사용자 정보를 가져오는 엔드포인트와 사용자 서비스를 설정, customUserService 설정
                                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                                .failureHandler(oAuth2LoginFailureHandler)  //OAuth2 로그인 실패시 처리할 핸들러 지정
                                .successHandler(oAuth2LoginSuccessHandler) // OAuth2 로그인 성공시 처리할 핸들러 지정
                );

        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     */
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(jwtService, userRepository);
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter(){
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);

        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }

    // cors(cross-origin-resource-sharing
    // origin - 스키마 + 도메인(+포트)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 접근 url 설정, 추후 허용하는 서버로 접근 지정
        config.setAllowedOriginPatterns(List.of("*"));

        // 허용할 rest api 목록, HttpMethod로 추후 허용하는 method만 추가
        config.setAllowedMethods(List.of("*"));

        // 허용할 헤더 목록(client에 보낼), 추후 사용하는 header만 지정 "X-Requested-With", "Content-Type", "Authorization", "X-XSRF-token"
        config.setAllowedHeaders(List.of("*"));

        // client에 노출 시킬 헤더(이 설정 없으면 client에서 해당 헤더를 가져오지 못함) (cors에서 몇몇 헤더외엔 노출 되지 않음) (cors에서 헤더 - 향후 헤더에 담아 보낼 key값 정의)
        config.setExposedHeaders(List.of("Authorification", "Role"));

        // 요청에 쿠키나 인증 토큰(jwt) 포함가능
        config.setAllowCredentials(true);

        /*
         * cross-origin 요청 전 options 메소드로 preflight 전송하는데
         * 이 결과를 지정한 시간동안 저장하도록 지정
         * */
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 지정한 패턴의 요청에 위에서 지정한 cors 정책 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
