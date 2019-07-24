package cn.itcast.travel.dao.route;

import cn.itcast.travel.domain.Route;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public interface RouteDao {
    List<Route> list(String cid);

    List<Route> pageQuery(int i, int i1, int pageSize);

    int queryTotalCounts(int i);

    Map<String,Object> findRouteDetailByRid(String rid);

    void update(JdbcTemplate template, String rid);

    Route findRouteByRid(int i);
}
