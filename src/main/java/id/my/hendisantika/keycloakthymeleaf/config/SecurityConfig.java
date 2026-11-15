package id.my.hendisantika.keycloakthymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-thymeleaf-sample3
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 13/10/24
 * Time: 07.11
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((req) -> req
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()

                )
                .oauth2Login(Customizer.withDefaults()) // Enable OAuth2 login with Keycloak
                .logout(logout -> logout
                        .logoutRequestMatcher(PathPatternRequestMatcher.withDefaults().matcher("/logout")).permitAll()
                        .logoutSuccessUrl("/")
                );
        return http.build();
    }
}
