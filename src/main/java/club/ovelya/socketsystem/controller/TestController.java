package club.ovelya.socketsystem.controller;

import club.ovelya.socketsystem.utils.R;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  private SessionDAO sessionDAO;

  @GetMapping("/hello")
  public R<?> hello() throws InterruptedException {
    StringBuilder sb = new StringBuilder();
    for (Session session : sessionDAO.getActiveSessions()) {
      SimplePrincipalCollection attribute = (SimplePrincipalCollection) session.getAttribute(
          "org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
      sb.append(attribute.getPrimaryPrincipal());
    }
    return R.custom(200, sb.toString(), null);
  }
}
