package com.smallbell.modules.app.config;


import com.smallbell.modules.app.form.monitor.UserDrivenResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * socket 建立成功事件
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object userId = session.getAttributes().get("userId");
        if (userId != null) {
            // 用户连接成功，放入在线用户缓存
            SESSION_POOL.put(userId.toString(), session);
        } else {
            throw new RuntimeException("用户登录已经失效!");
        }
    }

    /**
     * 接收消息事件
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object token = session.getAttributes().get("token");
        Object userId = session.getAttributes().get("userId");
        log.info("server 接收到 {}:{} 发送的 {}", userId, token, payload);
        session.sendMessage(new TextMessage("server 发送给 " + token + " 消息 " + payload + " " + LocalDateTime.now().toString()));
    }

    /**
     * socket 断开连接时
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object token = session.getAttributes().get("userId");
        if (token != null) {
            // 用户退出，移除缓存
            SESSION_POOL.remove(token.toString());
        }
    }

    /**
     * 给某个用户(token)发送消息 token即userId
     *
     * @param token
     * @param message
     */
    public void send(String token, String message) {
        WebSocketSession session = SESSION_POOL.get("token");
        if (session != null) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(token+"="+message) );
                }
            } catch (IOException e) {
                log.error("发送消息给{}:{}失败", token, session.getAttributes().get("token"), e);
            }
        }
        log.error("error");
    }

//    public static void sendInfo(String message, String userId) {
//        log.info("发送消息到:"+userId+"，报文:"+message);
//        if(StringUtils.isNotBlank(userId) && SESSION_POOL.containsKey(userId)){
//            SESSION_POOL.get(userId).sendMessage(message);
//        }else{
//            log.error("用户"+userId+",不在线！");
//        }
//    }

    public void sendAll(String message) {
        for (String token : SESSION_POOL.keySet()) {
            send(token, message);
        }
    }
}