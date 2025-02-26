package com.example.oauth2example.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request-> request.requestMatchers("/secure").authenticated()
                .anyRequest().permitAll())
                .formLogin(form -> form
                        .defaultSuccessUrl("/your-custom-path", true)
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/your-custom-path", true)
                );
        return http.build();

    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration github = gitHubClientRegistration();
        ClientRegistration facebook = facebookClientRegistration();
        return new InMemoryClientRegistrationRepository(github,facebook);
    }

    private ClientRegistration gitHubClientRegistration() {

        return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId(dotenv.get("GITHUB_CLIENT_ID"))
                .clientSecret(dotenv.get("GITHUB_CLIENT_SECRET")).build();
    }

    private ClientRegistration facebookClientRegistration() {
        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook").clientId(dotenv.get("FACEBOOK_CLIENT_ID"))
                .clientSecret(dotenv.get("FACEBOOK_CLIENT_SECRET")).build();
    }
}
