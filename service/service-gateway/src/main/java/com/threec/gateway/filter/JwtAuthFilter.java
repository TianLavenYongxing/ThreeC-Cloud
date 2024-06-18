package com.threec.gateway.filter;

import com.threec.tools.utils.R;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.alibaba.fastjson2.JSON;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Class LightweightAuthenticationFilter.
 * <p>
 * 配置轻鉴权过滤器 去中心化后 考虑服务自己实现鉴权 这里只负责认证 轻鉴权 强认证
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 18/6/24
 */

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public JwtAuthFilter() {
        super(Config.class);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                R<Object> r = new R<>().error(HttpStatus.UNAUTHORIZED.value(), "Authentication failed");
                String body = JSON.toJSONString(r);
                DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                return response.writeWith(Mono.just(buffer));
            }

            String token = authHeader.substring(7);
            try {
                Key key = getSignInKey();
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
                System.out.println(claims);
                // todo 逻辑完善 当前用户信息在redis中是否存在之类的
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 配置属性
    }
}

