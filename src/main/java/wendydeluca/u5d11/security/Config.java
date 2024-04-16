package wendydeluca.u5d11.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // stabilisco regole di autorizzazione sugli endpoints
public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 1. vado a disabilitare il form di login di default
        httpSecurity.formLogin(http ->http.disable());
        // 2. disabilito la protezione da csrf (un tipo di attacchi)
        httpSecurity.csrf(http->http.disable());
        //3. disabilito le sessioni in quanto l'autenticazione con token é stateless
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 4. vado ad autorizzare le richieste su tutti gli endpoint, poiché di default non sono autorizzate
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/**").permitAll());

        return httpSecurity.build();// ritorno un oggetto di tipo SecurityFilterChain

    }
}
