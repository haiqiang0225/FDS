package cc.seckill.config;

import cc.seckill.config.access.CustomAccessDeniedHandler;
import cc.seckill.config.access.CustomAuthenticationEntryPoint;
import cc.seckill.filters.TokenAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * description: SecurityConfig <br>
 * date: 2022/12/11 10:15 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private TokenAuthenticationFilter authenticationFilter;

    @Resource
    private UserDetailsService userDetailsService;

    public static final String[] PERMIT_ALL_URLS = {
            "/logout",
            "/image/**"
    };

    // 认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                //================================ 跨域 ============================//
                .cors()
                .and()
                //================================= csrf ==========================//
                .csrf().disable()

                //=============================== Session =========================//
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //================================  url拦截  ========================//
                .authorizeRequests()
                // 对于登录接口 只允许匿名访问
                .antMatchers("/login").anonymous()
                .antMatchers("/login/**").anonymous()
                // 不管是否登录都允许访问的
                .antMatchers(PERMIT_ALL_URLS).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()

                //================================ 异常处理 ========================//
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                //================================ 登陆登出 ========================//
                .and()
                // 禁用默认登录页
                .formLogin()
                .disable()
                // 禁用默认登出
                .logout()
                .disable()


                //=============================  自定义拦截器  =========================//
                //登陆拦截器
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


    // 授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("localhost:8080", "*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "HEAD", "DELETE", "OPTIONS", "*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
