package com.wallet.DigiPay.config;

import com.wallet.DigiPay.Interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AuthorizationInterceptor authorizationInterceptor ;

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

                .addPathPatterns("/users","/auth/user","/wallets","/wallets/{id}/transactions");
    }
}
