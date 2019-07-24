package cn.itcast.travel.service.user;

import cn.itcast.travel.dao.user.UserDao;
import cn.itcast.travel.dao.user.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.utils.Myfactory;

/**
 * 用户业务层对象
 * dao  data access object
 * 各司其职
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = Myfactory.getInstance(UserDao.class);

    @Override
    public User findUserByEmail(String email) {
        //  邮箱 查询 用户  select * from tab_user where email = ?
        //  jdbcTemplate
        User user = null;
        try {
            user = userDao.findUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserByTelephone(String telephone) {
        User user = null;
        try {
            user = userDao.findUserByTelephone(telephone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
