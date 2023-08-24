package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.dao.SysRoleRepository;
import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.entity.SysRole;
import club.ovelya.socketsystem.entity.UserInfo;
import club.ovelya.socketsystem.service.MailService;
import club.ovelya.socketsystem.service.UserInfoService;
import club.ovelya.socketsystem.utils.AESUtil;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

  private final ConcurrentHashMap<String, LocalDateTime> requestLimitMap = new ConcurrentHashMap<>();
  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private SysRoleRepository sysRoleRepository;
  @Autowired
  private MailService mailService;

  @Override
  public void registerUser(UserInfo userInfo) {
    if (userInfoRepository.findByUsername(userInfo.getUsername()) != null) {
      throw new RuntimeException("用户名已存在！");
    }
    if (userInfoRepository.findByEmail(userInfo.getEmail()) != null) {
      throw new RuntimeException("邮箱已存在！");
    }
    String hashed = BCrypt.hashpw(userInfo.getPassword(), BCrypt.gensalt());
    userInfo.setPassword(hashed);
    userInfoRepository.save(userInfo);
  }

  @Override
  public String loginUser(UsernamePasswordToken usernamePasswordToken) {
    String usernameOrEmail = usernamePasswordToken.getUsername();
    if ("".equals(usernameOrEmail)) {
      throw new RuntimeException("用户名不能为空");
    }
    if (Arrays.toString(usernamePasswordToken.getPassword()).isEmpty()) {
      throw new RuntimeException("密码不能为空");
    }
    Subject subject = SecurityUtils.getSubject();
    subject.login(usernamePasswordToken);
    UserInfo userInfo = userInfoRepository.findByUsernameOrEmail(usernameOrEmail);
    userInfo.setLastLoginTime(LocalDateTime.now());
    userInfo.setLastLoginIP(subject.getSession().getHost());
    userInfoRepository.save(userInfo);
    return AESUtil.encrypt(userInfo.getUsername(), userInfo.getPassword()).replace("/", "@");
  }

  @Override
  public void addRoleToUser(String username, String role) {
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    List<SysRole> roleList = userInfo.getRoleList();
    SysRole sysRole = sysRoleRepository.findByRole(role);
    roleList.add(sysRole);
    userInfoRepository.save(userInfo);
  }

  @Override
  public void sendVerifyMail() {
    String username = SecurityUtils.getSubject().getPrincipal().toString();
    LocalDateTime lastSendTime = requestLimitMap.get(username);
    if (username == null) {
      throw new RuntimeException("请先登录");
    }
    if (lastSendTime != null) {
      Duration duration = Duration.between(lastSendTime, LocalDateTime.now());
      //两分钟内只能发送一次
      if (duration.toMillis() < 120000) {
        throw new RuntimeException("请求太频繁");
      }
    }
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    if (userInfo.getState() == 1) {
      throw new RuntimeException("账号已激活");
    }
    String encodeUsername = AESUtil.encrypt(username);
    mailService.sendVerifyMail(userInfo.getEmail(), encodeUsername);
    requestLimitMap.put(username, LocalDateTime.now());
  }

  @Override
  public void verifyUser(String encodeUsername) {
    String encodeUsernamePlaceRe = encodeUsername.replace("@", "/");
    String decodeUsername = AESUtil.decrypt(encodeUsernamePlaceRe);
    UserInfo userInfo = userInfoRepository.findByUsername(decodeUsername);
    if (userInfo.getState() == 1) {
      throw new RuntimeException("账号已激活，请勿重复验证");
    }
    //激活账号
    userInfo.setState(1);
    //加上普通用户角色
    List<SysRole> roleList = userInfo.getRoleList();
    SysRole sysRole = sysRoleRepository.findByRole("user");
    roleList.add(sysRole);
    userInfoRepository.save(userInfo);
  }
}
