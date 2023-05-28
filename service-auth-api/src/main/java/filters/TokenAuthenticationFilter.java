package cc.seckill.filters;

import cc.seckill.enums.ResultStatusCodes;
import cc.seckill.exceptions.GlobalException;
import cc.seckill.service.LoginService;
import cc.seckill.service.UserService;
import cc.seckill.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * description: TokenAuthenticationFilter Token认证过滤器<br>
 * date: 2022/12/11 11:41 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        // 获取token, 如果没有token或者token失效,直接放行(不设置Security上下文)
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 尝试解析token,如果token存在且未过期的话会返回对象
        Claims claims;
        try {
            claims = JwtUtil.parseJWTifNotExpired(token);
            if (Objects.isNull(claims)) {
                throw new GlobalException(ResultStatusCodes.LOGIN_FAIL);
            }
            String tokenExist = stringRedisTemplate.opsForValue().get(LoginService.getKeyForRedis(claims));
            if (Objects.isNull(tokenExist)) {
                throw new AccountExpiredException("后端(redis)账号登陆状态过期");
            }
        } catch (ExpiredJwtException e) {
            filterChain.doFilter(request, response);
            return;
//            throw new AccountExpiredException("前端(token)账号登陆状态过期");
        } catch (RedisSystemException e) {
            e.printStackTrace();
            HashMap<String, String> map = new HashMap<>(2);
            map.put("uri", request.getRequestURI());
            map.put("msg", "出问题了,请稍后重试!");

            ObjectMapper objectMapper = new ObjectMapper();
            String resBody = objectMapper.writeValueAsString(map);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(resBody);
            printWriter.flush();
            printWriter.close();
            filterChain.doFilter(request, response);
            return;
        }

        // 如果解析到了token,设置Security上下文后放行
        String username = claims.get("username").toString();
        log.info("用户: {},使用token认证", username);

        Collection<? extends GrantedAuthority> userAuthorities =
                userService.getUserAuthoritiesByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null,
                        userAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
