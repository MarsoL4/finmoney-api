package br.com.fiap.fin_money_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*") //será alterado depois somente para o endereço real
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*");

    }
    
    

}
