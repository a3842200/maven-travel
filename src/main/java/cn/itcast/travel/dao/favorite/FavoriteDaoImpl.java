package cn.itcast.travel.dao.favorite;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.utils.C3p0Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    @Override
    public Favorite isnotFavorite(int rid, int uid) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_favorite where uid = ? and rid = ?";
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Favorite.class),uid,rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void addFavorite(JdbcTemplate template, int rid, int uid, String format) {
        try {
            String sql ="insert into tab_favorite values(?,?,?)";
            template.update(sql,rid,format,uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
