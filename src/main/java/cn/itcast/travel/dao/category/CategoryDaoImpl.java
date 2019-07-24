package cn.itcast.travel.dao.category;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.utils.C3p0Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_category";
            List<Category> list =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Category.class));
            return list;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
