package cc.seckill.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * description: WebMvcConfig <br>
 * date: 2022/12/30 11:02 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Configuration
public class WebMvcConfig implements WebFluxConfigurer {




    /**
     * description: addResourceHandlers 配置静态资源访问 <br>
     * version: 1.0 <br>
     * date: 2022/12/30 17:02 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024);
    }

    @Bean
    public RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.route()
                .GET("/static/**", this::handleStaticResource)
                .GET("/image/**", this::handleImageResource)
                .build();
    }

    private Mono<ServerResponse> handleStaticResource(ServerRequest request) {
        String path = request.path().substring("/static".length());
        ClassPathResource resource = new ClassPathResource("static" + path);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(BodyInserters.fromResource(resource));
    }

    private Mono<ServerResponse> handleImageResource(ServerRequest request) {
        String path = request.path().substring("/image".length());
        ClassPathResource resource = new ClassPathResource("static/image" + path);
        return ServerResponse.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(BodyInserters.fromResource(resource));
    }
}
