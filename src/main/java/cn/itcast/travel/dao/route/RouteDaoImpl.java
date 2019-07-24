package cn.itcast.travel.dao.route;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.utils.C3p0Utils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements RouteDao {
    @Override
    public List<Route> list(String cid) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_route where cid = ?";
            List<Route> list =jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),Integer.parseInt(cid));
            return list;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<Route> pageQuery(int cid, int startIndex, int pageSize) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_route where cid = ? limit ?,?";
            List<Route> list =jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Route.class),cid,startIndex,pageSize);
            return list;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public int queryTotalCounts(int cid) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select count(1) from tab_route where cid = ? ";
            int num = jdbcTemplate.queryForObject(sql,Integer.class,cid);
            return num;
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> findRouteDetailByRid(String rid) {
        String sql="select * from tab_category as c , tab_route as r , tab_seller as s where c.cid = r.cid and s.sid = r.sid and r.rid = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
        Map<String,Object> map = jdbcTemplate.queryForMap(sql,Integer.parseInt(rid));
        return map;
    }

    @Override
    public void update(JdbcTemplate template, String rid) {
        try {
        String sql ="update tab_route set count=count+1 where rid = ?";
          template.update(sql,rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    @Override
    public Route findRouteByRid(int rid) {
        try {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(C3p0Utils.getDataSource());
            String sql ="select * from tab_route where rid = ?";
            return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
