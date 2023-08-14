package club.ovelya.socketsystem.service.impl;

import club.ovelya.socketsystem.dao.UserInfoRepository;
import club.ovelya.socketsystem.domain.UserInfo;
import club.ovelya.socketsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public String registerUser(String username, String name, String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            userInfoRepository.save(new UserInfo(username, name, hashed));
            return "success";
        } catch (Exception e) {
            return e.toString();
        }

    }
}
