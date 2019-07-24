package cn.itcast.travel.service.route;

import cn.itcast.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteService {
    List<Route> list(String cid);

    void pageQuery(Map<String, Object> map, String cid, int page, int pageSize);

    Route findRouteDetailByRid(String rid);

    Route findRouteByRid(String rid);
}
