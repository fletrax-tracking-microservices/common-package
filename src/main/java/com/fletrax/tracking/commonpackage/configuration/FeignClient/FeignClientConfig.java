package com.fletrax.tracking.commonpackage.configuration.FeignClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;

// import feign.RequestInterceptor;
// import feign.RequestTemplate;
// import feign.codec.Encoder;
// import feign.form.spring.SpringFormEncoder;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Extract the JWT token from the security context
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof JwtAuthenticationToken jwtToken) {
                    String token = jwtToken.getToken().getTokenValue();
                    template.header("Authorization", "Bearer " + token);
                }
            }
        };
    }

    // @Bean
    // public Encoder multipartFormEncoder() {
    //     return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
    //         @SuppressWarnings("null")
    //         @Override
    //         public HttpMessageConverters getObject() throws BeansException {
    //             return new HttpMessageConverters(new RestTemplate().getMessageConverters());
    //         }
    //     }));
    // }

}
