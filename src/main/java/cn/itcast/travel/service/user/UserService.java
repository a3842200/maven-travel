package cn.itcast.travel.service.user;

import cn.itcast.travel.domain.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByTelephone(String telephone);
}
