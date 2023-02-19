package com.example.todolist.config;

import com.example.todolist.User.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    /*
        A configuration file is loaded at the start time and available for all the components
    */
    private  final UserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        /*
            Declaring it as bean and it should be public always
        */
        return username -> repository
                .findByEmail(username)
                .orElseThrow(
                        ()->new UsernameNotFoundException("User Not Found!!!")
                );
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncode());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public WebMvcConfigurer corsMappingConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method",
//                                "Access-Control-Request-Headers")
//                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//                        .allowedOrigins("*")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowCredentials(true)
//                        .maxAge(3600);
//            }
//        };
//    };
}
