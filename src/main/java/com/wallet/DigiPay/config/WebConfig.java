package com.wallet.DigiPay.config;

import com.wallet.DigiPay.Interceptor.AuthorizationInterceptor;
import com.wallet.DigiPay.entities.RoleType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:9091")
                .allowCredentials(true);


    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //users access to these addresses
        registry.addInterceptor(authorizationInterceptor)

                .addPathPatterns("/users", "/auth/user", "/wallets", "/wallets/{id}/transactions");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/users/**").permitAll()
                .antMatchers("/test/**").permitAll()
                .mvcMatchers("/wallets/**").hasAuthority(RoleType.User.toString())
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic();


        return http.build();
    }
}
