package cc.seckill.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * description: WebMvcConfig <br>
 * date: 2022/12/30 11:02 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
//                .allowCredentials(true)
                .allowedMethods("", "").maxAge(3600);
    }

    /**
     * description: addResourceHandlers 配置静态资源访问 <br>
     * version: 1.0 <br>
     * date: 2022/12/30 17:02 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    @Override
    public void addResourceHandlers(@NotNull ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
    }
}
