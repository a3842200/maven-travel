package cn.itcast.travel.service.favorite;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Pagination;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {
    Favorite isnotFavorite(String rid, int uid);

    void addFavorite(String rid, int uid);

    void pageQuery(Pagination<Route> pagination, int uid);
}
