package cn.itcast.travel.dao.user;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findUserByEmail(String email);

    User findUserByTelephone(String telephone);
}
