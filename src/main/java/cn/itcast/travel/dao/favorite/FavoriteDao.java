package cn.itcast.travel.dao.favorite;

import cn.itcast.travel.domain.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;

public interface FavoriteDao {
    Favorite isnotFavorite(int i, int uid);

    void addFavorite(JdbcTemplate template, int rid, int uid, String format);
}
