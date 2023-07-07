package springilmiofotoalbum.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/create").hasAnyAuthority("ADMIN")
                .requestMatchers("/edit/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/delete/**").hasAnyAuthority("ADMIN")
                .requestMatchers("/categoria").hasAnyAuthority("ADMIN")
                .requestMatchers("/detail/**").hasAnyAuthority("ADMIN", "USER")
//                .requestMatchers(HttpMethod.POST).hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        http.csrf().disable();
        return http.build();
    }

}
