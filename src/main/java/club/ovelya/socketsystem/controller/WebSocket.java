package club.ovelya.socketsystem.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocket {
    // session集合,存放对应的session
    private static final ConcurrentHashMap<Integer, Session> sessionPool = new ConcurrentHashMap<>();
    // concurrent包的线程安全Set,用来存放每个客户端对应的WebSocket对象。
    private static final CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 推送消息到指定用户
     *
     * @param userId  用户ID
     * @param message 发送的消息
     */
    public static void sendMessageByUser(Integer userId, String message) {
        log.info("用户ID：" + userId + ",推送内容：" + message);
        Session session = sessionPool.get(userId);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("推送消息到指定用户发生错误：" + e.getMessage(), e);
        }
    }

    /**
     * 群发消息
     *
     * @param message 发送的消息
     */
    public static void sendAllMessage(String message) {
        log.info("发送消息：{}", message);
        for (WebSocket webSocket : webSocketSet) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("群发消息发生错误：" + e.getMessage(), e);
            }
        }
    }

    /**
     * 建立WebSocket连接
     *
     * @param session
     * @param userId  用户ID
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Integer userId) {
        log.info("WebSocket建立连接中,连接用户ID：{}", userId);
        Session historySession = sessionPool.get(userId);
        // historySession不为空,说明已经有人登陆账号,应该删除登陆的WebSocket对象
        if (historySession != null) {
            webSocketSet.remove(historySession);
            try {
                historySession.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 建立连接
        this.session = session;
        webSocketSet.add(this);
        sessionPool.put(userId, session);
        log.info("建立连接完成,当前在线人数为：{}", webSocketSet.size());
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        log.debug(throwable.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("连接断开,当前在线人数为：{}", webSocketSet.size());
    }

    /**
     * 接收客户端消息
     *
     * @param message 接收的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到客户端发来的消息：{}", message);
    }
}
