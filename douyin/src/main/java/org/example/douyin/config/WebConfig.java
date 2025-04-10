package org.example.douyin.config;

import org.example.douyin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bgmyangzhu
 * @date 2025/4/1 23:49
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor(userService))
                .addPathPatterns("/admin/**", "authorize/**")
                .addPathPatterns("/douyin/**")
                .excludePathPatterns("/douyin/login/**", "/douyin/index/**","/douyin/file/**", "douyin/cdn/**");
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//        String[] url = {"http://101.35.228.84:8882","http://101.35.228.84:5378","http://127.0.0.1:5378","http://localhost:5378"};
//
//        registry.addMapping("/**")
//                .allowedOrigins(url)
//                .allowCredentials(true)
//                .allowedMethods("*")   // 允许跨域的方法，可以单独配置
//                .allowedHeaders("*");  // 允许跨域的请求头，可以单独配置;
//    }
}
