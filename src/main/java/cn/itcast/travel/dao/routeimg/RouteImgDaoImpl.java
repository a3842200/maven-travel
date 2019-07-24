package cn.itcast.travel.dao.routeimg;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.utils.C3p0Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    @Override
    public List<RouteImg> findRouteImageByRid(int rid) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_route_img where rid = ?";
            List<RouteImg> list =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
            return list;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
