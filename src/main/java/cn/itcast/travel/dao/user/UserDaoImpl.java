package cn.itcast.travel.dao.user;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.utils.C3p0Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByEmail(String email) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_user where email = ?";
            User user =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),email);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public User findUserByTelephone(String telephone) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_user where telephone = ?";
            User user =  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),telephone);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
