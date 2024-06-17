package com.threec.gateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Class DetailedLoggingFilter.
 * <p>
 * DetailedLoggingFilter
 * </p>
 * <p>
 * 请求处理时间: 记录请求开始时间和结束时间
 * 响应信息: 响应状态码和响应头部、响应体内容
 * 如果在处理请求时发生错误，记录错误详细信息和堆栈跟踪。
 *
 * @author laven
 * @version 1.0
 * @since 17/6/24
 */
@Component
@Slf4j
public class DetailedLoggingFilter implements GlobalFilter, Ordered {
    private static final String TRACE_ID_HEADER = "Trace-ID";
    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst(TRACE_ID_HEADER);
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
            exchange = exchange.mutate().request(exchange.getRequest().mutate().header(TRACE_ID_HEADER, traceId).build()).build();
        }
        ServerWebExchange finalExchange = exchange;  // 创建一个新的局部变量
        LocalDateTime start = LocalDateTime.now();
        return logRequestDetails(exchange.getRequest(), start) .then(chain.filter(exchange))  .then(Mono.defer(() -> {
                    LocalDateTime end = LocalDateTime.now();
                    Duration duration = Duration.between(start, end);
                    return logResponseDetails(finalExchange.getResponse(), start, duration);
                }));
    }

    /**
     * todo WebFlux环境中 无法从response中 获取到响应内容 需各个服务自行实现
     * @param response response
     * @param start 请求开始时间
     * @param duration 请求执行时间
     * @return mono
     */
    private Mono<Void> logResponseDetails(ServerHttpResponse response, LocalDateTime start, Duration duration) {
        // 记录响应状态码和响应头
        StringBuilder headersInfo = new StringBuilder();
        response.getHeaders().forEach((key, value) -> headersInfo.append(key).append(": ").append(String.join(", ", value)).append("; "));
        return Mono.fromRunnable(() -> {
            log.info("Response Status Code: {}, Request start time: {}, Duration: {} ms",
                    response.getStatusCode(), start, duration.toMillis());
        });
    }

    /**
     * todo 敏感信息加密 脱敏
     * @param request 基本请求信息: HTTP 方法、请求 URI、HTTP 协议版本(未获取)
     *                客户端信息: 客户端 IP 地址 端口号、User-Agent
     *                请求头部: 所有请求头部：包括认证信息（如 Authorization）、内容类型（Content-Type）、接受类型（Accept）等
     *                请求体内容: 对于 POST 和 PUT 请求，请求体是很重要的信息源。但要注意，记录大的请求体可能会影响性能和日志的可管理性。
     */
    private Mono<Void> logRequestDetails(ServerHttpRequest request, LocalDateTime star) {
        // 构建请求头部信息的字符串
        StringBuilder headersInfo = new StringBuilder();
        request.getHeaders().forEach((key, value) -> headersInfo.append(key).append(": ").append(String.join(", ", value)).append("; "));
        // 获取客户端IP地址和端口号
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        String clientIpAddress = remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : "Unknown";
        int clientPort = remoteAddress != null ? remoteAddress.getPort() : -1;
        // 获取User-Agent
        String userAgent = request.getHeaders().getFirst("User-Agent");
        HttpMethod method = request.getMethod();
        // 确认是否需要读取请求体
        if ("POST".equals(method.toString()) || "PUT".equals(method.toString())) {
            return DataBufferUtils.join(request.getBody()).map(dataBuffer -> {
                byte[] bytes = new byte[dataBuffer.readableByteCount()];
                dataBuffer.read(bytes);
                DataBufferUtils.release(dataBuffer);
                return bytes;
            }).defaultIfEmpty(new byte[0]).doOnNext(bytes -> {
                String bodyContent = new String(bytes);
                if (!StringUtils.isEmpty(bodyContent) && bodyContent.length() < 1024) { // 控制日志内容的大小
                    log.info("Request Body: {}", bodyContent);
                }
            }).then(Mono.fromRunnable(() -> {
                log.info("Request Method: {}, URI: {}, Client IP: {}, Client Port: {}, User-Agent: {}, Request Headers: {}, Request start time: {}", method, request.getURI(), clientIpAddress, clientPort, userAgent, headersInfo, star);
            }));
        } else {
            log.info("Request Method: {}, URI: {}, Client IP: {}, Client Port: {}, User-Agent: {}, Request Headers: {}, Request start time: {}", method, request.getURI(), clientIpAddress, clientPort, userAgent, headersInfo, star);
            return Mono.empty();
        }
    }

}