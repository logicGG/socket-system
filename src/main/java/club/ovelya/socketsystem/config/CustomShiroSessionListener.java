package club.ovelya.socketsystem.config;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

@Slf4j
@Getter
public class CustomShiroSessionListener implements SessionListener {

  /**
   * 维护着个原子类型的Integer对象，用于统计在线Session的数量
   */
  private final AtomicInteger sessionCount = new AtomicInteger(0);

  @Override
  public void onStart(Session session) {
    sessionCount.getAndIncrement();
    log.debug("用户登录，用户IP:" + session.getHost());
  }

  @Override
  public void onStop(Session session) {
    sessionCount.decrementAndGet();
    log.debug("用户退出，用户IP:" + session.getHost());
  }

  @Override
  public void onExpiration(Session session) {
    sessionCount.decrementAndGet();
    log.debug("用户过期，用户IP:" + session.getHost());
  }

}
