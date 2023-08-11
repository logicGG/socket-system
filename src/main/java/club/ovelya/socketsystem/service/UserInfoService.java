package club.ovelya.socketsystem.service;

import club.ovelya.socketsystem.domain.UserInfo;

public interface UserInfoService {
    UserInfo findByUsername(String username);
}
