package club.zhangliyuanblog.config;

import club.zhangliyuanblog.interceptor.JWTInterceptor;
import club.zhangliyuanblog.properties.SystemProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liyuan.zhang
 * @date 2021/3/13 21:34
 * 拦截器
 */
@Configuration
@EnableConfigurationProperties(SystemProperties.class)
public class InterceptorConfig implements WebMvcConfigurer {

    private final SystemProperties systemProperties;

    public InterceptorConfig(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 如果是debug模式，所以的接口的都不拦截
        if (systemProperties.isDebug()){
            return;
        }
        String[] swaggerExcludes=new String[]{
                // swagger
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**",
                "/v2/api-docs/**",
                // 静态资源
                "favicon.ico",
                "/css/**",
                "/js/**",
                "/image/**",
                "/index.html",
                "/*.pdf",
                "/static/**",
                "/*/*.css",
                "/*/*.js",
                "/*.jpg",
                // TODO 要放行的路径
                "/user/**",
                "/captcha/**"
                };
        registry.addInterceptor(new JWTInterceptor())
                // ToDo 配置拦截路径
                .addPathPatterns("/**")
                .excludePathPatterns(swaggerExcludes);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
