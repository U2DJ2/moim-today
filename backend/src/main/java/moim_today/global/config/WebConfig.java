package moim_today.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import moim_today.global.argumentresolver.AdminLoginArgumentResolver;
import moim_today.global.argumentresolver.MemberLoginArgumentResolver;
import moim_today.global.interceptor.AdminLoginInterceptor;
import moim_today.global.interceptor.MemberLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    public WebConfig(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new MemberLoginInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/login",
                        "/api/certification/**",
                        "/api/sign-up",
                        "/api/session-validation",
                        "/api",
                        "/api/universities",
                        "/api/universities/departments/**",
                        "/api/departments/university-name",
                        "/api/departments/university-id",
                        "/api/departments",
                        "/api/request-departments",
                        "/api/moims/categories",
                        "/api/initiation",
                        "/api/admin/**"
                );

        registry.addInterceptor(new AdminLoginInterceptor())
                .order(2)
                .addPathPatterns("/api/admin/**")
                .excludePathPatterns(
                        "/api/admin/login"
                );
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberLoginArgumentResolver(objectMapper));
        resolvers.add(new AdminLoginArgumentResolver(objectMapper));
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:8080", "http://localhost:9000", "https://moim.today", "https://api.moim.today")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .exposedHeaders("*");
    }
}
