package com.ngotu.demo.security;

import com.ngotu.demo.model.UserLogin;
import com.ngotu.demo.repo.FaceDataUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created at 24/07/2024 by Ngo Tu
 *
 * @author: Ngo Tu
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//        @Bean
//        @ConditionalOnMissingBean(UserDetailsService.class)
//        // quản lý danh sách các user
//        InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//
//            String matKhauEmTrang = passwordEncoder().encode("123");
//            String matKhauAnhTu = passwordEncoder().encode("123");
//
//            return new InMemoryUserDetailsManager(
//                    User
//                            .withUsername("trang")
//                            .password(matKhauEmTrang)
//                            .roles("USER")
//                            .build(),
//                    User
//                            .withUsername("ngotu")
//                            .password(matKhauAnhTu)
//                            .roles("USER")
//                            .build()
//            );
//        }

    private final FaceDataUserRepo faceDataUserRepo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/trang")
                        .hasRole("TRANG")
                        .requestMatchers("/api/tu")
                        .hasRole("TU")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            UserLogin u = faceDataUserRepo.UserfindByUserName(username);
            if (u == null) {
                throw new UsernameNotFoundException("Sai rồi!");
            }
            return u;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
        return new DefaultAuthenticationEventPublisher(delegate);
    }
}

