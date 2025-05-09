package br.com.fiap.fin_money_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
            auth -> auth
                //.requestMatchers("/categories/**").hasRole("ADMIN") //Somente usuários com o role de ADMIN poderão fazer requisições nas endpoints de categories
                .requestMatchers("/login/**").permitAll() //Essa requisição não precisa de autenticação (pode ser feita por qualquer um)
                .anyRequest().authenticated() //Qualquer usuário pode fazer requisições nas outras endpoints desde que esteja logado e autenticado
                
        )
        .csrf(csrf -> csrf.disable()) //Por ser uma APIRest não faz sentido ter CSRF
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults())
        .build();
    }
    
    // @Bean
    // UserDetailsService userDetailsService(){
    //     var users = List.of(
    //         User
    //             .withUsername("enzo")
    //             .password("$2a$12$m36H8g.FxeIIw1vWq2rPie0P6cngNz.UF58nhTM7fCGl0B/bE227G") // -> Hash de "12345" gerado no site da Bcrypt
    //             .roles("ADMIN")
    //             .build(),
    //         User
    //             .withUsername("maria")
    //             .password("$2a$12$jdyNbLthZReEA3D1.ZTdvuUQnyhe9TDZcSlxH96DjSC2o.rPiHr5q") // -> Hash de "12345" gerado pela segunda vez no site da Bcrypt
    //             .roles("USER")
    //             .build()
    //     );
    //     return new InMemoryUserDetailsManager(users);
    // }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    //Versão de EXEMPLO sem Criptografia:
    // @Bean
    // UserDetailsService userDetailsService(){
    //     var users = List.of(
    //         User
    //             .withUsername("enzo")
    //             .password("{noop}12345") //{noop} -> Sem Criptografia
    //             .build(),
    //         User
    //             .withUsername("maria")
    //             .password("{noop}12345")
    //             .build()
    //     );
    //     return new InMemoryUserDetailsManager(users);
    // }
}