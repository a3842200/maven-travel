package cn.itcast.travel.dao.routeimg;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    List<RouteImg> findRouteImageByRid(int i);
}
