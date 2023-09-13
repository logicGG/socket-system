package club.ovelya.socketsystem.service.user;

import club.ovelya.socketsystem.dao.user.SysRoleRepository;
import club.ovelya.socketsystem.dao.user.UserInfoRepository;
import club.ovelya.socketsystem.entity.user.SysRole;
import club.ovelya.socketsystem.entity.user.UserInfo;
import club.ovelya.socketsystem.pojo.dto.user.LoginDTO;
import club.ovelya.socketsystem.pojo.dto.user.RegisterDTO;
import club.ovelya.socketsystem.service.mail.MailService;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

  private final ConcurrentHashMap<String, LocalDateTime> requestLimitMap = new ConcurrentHashMap<>();
  @Autowired
  private UserInfoRepository userInfoRepository;
  @Autowired
  private SysRoleRepository sysRoleRepository;
  @Autowired
  private MailService mailService;

  /**
   * 用户注册
   *
   * @param registerDTO 用户对象
   */
  public void registerUser(RegisterDTO registerDTO) {
    UserInfo userInfo = new UserInfo();
    BeanUtils.copyProperties(registerDTO, userInfo);
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

  /**
   * 用户登录
   *
   * @param loginDTO 账号密码
   * @return websocket的授权码
   */
  public String loginUser(LoginDTO loginDTO) {
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken();
    usernamePasswordToken.setUsername(loginDTO.getUsername());
    usernamePasswordToken.setPassword(loginDTO.getPassword().toCharArray());
    String usernameOrEmail = usernamePasswordToken.getUsername();
    if ("".equals(usernameOrEmail)) {
      throw new RuntimeException("用户名不能为空");
    }
    if (Arrays.toString(usernamePasswordToken.getPassword()).isEmpty()) {
      throw new RuntimeException("密码不能为空");
    }
    Subject subject = SecurityUtils.getSubject();
    subject.login(usernamePasswordToken);
    UserInfo userInfo = userInfoRepository.findByUsernameOrEmailOrPhoneNumber(usernameOrEmail);
    userInfo.setLastLoginTime(LocalDateTime.now());
    userInfo.setLastLoginIP(subject.getSession().getHost());
    userInfoRepository.save(userInfo);
    return AESUtil.encrypt(userInfo.getUsername(), userInfo.getPassword()).replace("/", "@");
  }

  /**
   * 添加角色给用户
   *
   * @param username 账号
   * @param role     角色
   */
  public void addRoleToUser(String username, String role) {
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    List<SysRole> roleList = userInfo.getRoleList();
    SysRole sysRole = sysRoleRepository.findByRole(role);
    roleList.add(sysRole);
    userInfoRepository.save(userInfo);
  }

  /**
   * 发送验证邮件
   */
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

  /**
   * 给用户认证
   *
   * @param encodeUsername 加密后的账号
   */
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