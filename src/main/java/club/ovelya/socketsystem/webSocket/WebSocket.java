package club.ovelya.socketsystem.webSocket;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.dao.WebSocketMessageRepository;
import club.ovelya.socketsystem.entity.UserInfo;
import club.ovelya.socketsystem.entity.WebSocketMessage;
import club.ovelya.socketsystem.utils.AESUtil;
import club.ovelya.socketsystem.utils.WebSocketMessageUtils.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//ServerEndpoint注解导致的多例模式
@ServerEndpoint("/websocket/{username}/{token}")
@Component
@Slf4j
public class WebSocket {

  // session集合,存放对应的session
  private static final ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
  //方便根据session查找username
  private static final ConcurrentHashMap<Session, String> reSessionPool = new ConcurrentHashMap<>();

  //多例类需要用static+set方法自动注入
  private static UserInfoRepository userInfoRepository;
  private static WebSocketMessageRepository webSocketMessageRepository;
  private static ObjectMapper objectMapper;

  /**
   * 推送消息到指定用户
   *
   * @param username 用户ID
   * @param message  发送的消息
   */
  public static void sendMessageByUser(String username, String message) {
    Session session = sessionPool.get(username);
    sendTextBySession(session, message);
  }

  /**
   * 群发消息
   *
   * @param message 发送的消息
   */
  public static void sendAllMessage(String message) {
    for (Map.Entry<String, Session> entry : sessionPool.entrySet()) {
      Session session = entry.getValue();
      sendTextBySession(session, message);
    }
  }

  /**
   * 传入session发送消息
   *
   * @param session 会话对象
   * @param message 发送的消息
   */
  private static void sendTextBySession(Session session, String message) {
    try {
      session.getBasicRemote().sendText(message);
    } catch (IOException e) {
      log.debug(e.getMessage());
    }
  }

  /**
   * 关闭session
   *
   * @param session 会话对象
   */
  private static void closeBySession(Session session) {
    try {
      session.close();
    } catch (IOException e) {
      log.debug(e.getMessage());
    }
  }

  /**
   * 获取在线用户
   *
   * @return 在线用户集合
   */
  public static Enumeration<String> getActiveUser() {
    return sessionPool.keys();
  }

  @Autowired
  public void setWebSocketMessageRepository(
      WebSocketMessageRepository webSocketMessageRepository) {
    WebSocket.webSocketMessageRepository = webSocketMessageRepository;
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    WebSocket.objectMapper = objectMapper;
  }

  /**
   * 多例模式下用set方法自动注入
   */
  @Autowired
  public void setUserInfoRepository(
      UserInfoRepository userInfoRepository) {
    WebSocket.userInfoRepository = userInfoRepository;
  }

  /**
   * 建立WebSocket连接
   *
   * @param session  会话对象
   * @param username 账号
   * @param token    授权码
   */
  @OnOpen
  public void onOpen(Session session, @PathParam(value = "username") String username,
      @PathParam(value = "token") String token) {
    String deToken = token.replace("@", "/");
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    String decrypted = AESUtil.decrypt(deToken, userInfo.getPassword());
    if (decrypted == null || !decrypted.equals(username)) {
      sendTextBySession(session, "没有授权，即将断开连接");
      closeBySession(session);
      return;
    }
    Session historySession = sessionPool.get(username);
    // historySession不为空,说明已经有人登陆账号,应该删除登陆的WebSocket对象
    if (historySession != null) {
      sessionPool.remove(username);
      reSessionPool.remove(historySession);
      closeBySession(historySession);
    }
    // 建立连接
    sessionPool.put(username, session);
    reSessionPool.put(session, username);
    sendTextBySession(session, "欢迎");
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
   *
   * @param session 会话对象
   */
  @OnClose
  public void onClose(Session session) {
    reSessionPool.remove(session);
    sessionPool.values().remove(session);
  }

  /**
   * 接收客户端消息
   *
   * @param message 消息
   * @param session 会话
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    String fromWho = reSessionPool.get(session);
    try {
      //校验消息格式
      WebSocketMessage webSocketMessage = objectMapper.readValue(message, WebSocketMessage.class);
      String toWho = webSocketMessage.getToWho();
      Type type = webSocketMessage.getType();
      String content = webSocketMessage.getContent();
      if (toWho == null || type == null || content == null) {
        sendTextBySession(session, "消息格式不正确");
        return;
      }
      webSocketMessage.setFromWho(fromWho);
      //保存消息记录
      webSocketMessageRepository.save(webSocketMessage);
    } catch (JsonProcessingException e) {
      sendTextBySession(session, e.getMessage());
    }
  }

}
