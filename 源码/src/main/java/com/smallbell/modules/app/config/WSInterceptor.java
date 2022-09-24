package com.smallbell.modules.app.config;

import org.apache.commons.lang.StringUtils;
import com.smallbell.modules.app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class WSInterceptor implements HandshakeInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 握手前
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 获得请求参数
        String authToken = ((ServletServerHttpRequest) request).getServletRequest().getHeader("token");
        System.out.println(authToken);
        if (StringUtils.isNotBlank(authToken)) {
                // 放入属性域
                Claims claims = jwtUtils.getClaimByToken(authToken);
                attributes.put("token", authToken);
                attributes.put("userId", Long.parseLong(claims.getSubject()));
                log.info("用户{}握手成功", Long.parseLong(claims.getSubject()));
                return true;

        }
        log.info("握手失败，用户登录已失效");
        return false;
    }

//    @Override
//    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
//        return false;
//    }

    /**
     * 握手后
     * @param request
     * @param response
     * @param wsHandler
     * @param exception
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手完成");
    }
}