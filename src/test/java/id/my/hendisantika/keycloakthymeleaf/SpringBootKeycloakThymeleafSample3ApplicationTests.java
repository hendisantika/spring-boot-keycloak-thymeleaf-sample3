package id.my.hendisantika.keycloakthymeleaf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@SpringBootTest(properties = "spring.docker.compose.enabled=false")
class SpringBootKeycloakThymeleafSample3ApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * Static client registration so the context can start without a running Keycloak
     * (the default auto-configuration performs OIDC discovery against the issuer-uri).
     */
    @TestConfiguration
    static class OAuth2TestConfig {

        @Bean
        ClientRegistrationRepository clientRegistrationRepository() {
            String base = "http://localhost:9080/realms/Greylian/protocol/openid-connect";
            ClientRegistration keycloak = ClientRegistration.withRegistrationId("keycloak")
                    .clientId("spring-boot-thymeleaf-keycloak")
                    .clientSecret("test-secret")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .scope("openid")
                    .authorizationUri(base + "/auth")
                    .tokenUri(base + "/token")
                    .jwkSetUri(base + "/certs")
                    .userInfoUri(base + "/userinfo")
                    .userNameAttributeName("preferred_username")
                    .build();
            return new InMemoryClientRegistrationRepository(keycloak);
        }
    }
}
