package cn.itcast.travel.service.favorite;

import cn.itcast.travel.dao.favorite.FavoriteDao;
import cn.itcast.travel.dao.favorite.FavoriteDaoImpl;
import cn.itcast.travel.dao.route.RouteDao;
import cn.itcast.travel.dao.route.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Pagination;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.utils.C3p0Utils;
import cn.itcast.travel.utils.Myfactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//  Spring   Bean工厂 对象实例解耦
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = Myfactory.getInstance(FavoriteDao.class);
    private RouteDao routeDao = new RouteDaoImpl();
//  properties  applicationContext.xml  (Dom4j+Xpath)    工厂对象  配置文件  泛型模板  反射！
    @Override
    public Favorite isnotFavorite(String rid, int uid) {
        Favorite favorite=null;
        try {
           return  favoriteDao.isnotFavorite(Integer.parseInt(rid),uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public void addFavorite(String rid, int uid) {

        //   favorite  insert       route  update  xxx  set count =count+1 where rid = ?
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //同时操作sql语句，需要使用事务
        //获取数据源
        DataSource dataSource = C3p0Utils.getDataSource();
        //实例jdbcTemplate
        JdbcTemplate template = new JdbcTemplate(dataSource);
        //启动事务管理器（获取datasource操作数据库连接对象并绑定到当前线程中）
        TransactionSynchronizationManager.initSynchronization();
        //从数据源中获取jdbcTemplate操作的当前连接对象
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            connection.setAutoCommit(false);
            favoriteDao.addFavorite(template,Integer.parseInt(rid),uid,format);
            routeDao.update(template,rid);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                TransactionSynchronizationManager.clearSynchronization();
            }
        }


    }

    @Override
    public void pageQuery(Pagination<Route> pagination, int uid) {
//  学员完成 dao查询！
//        pagination.setData(data);
//        pagination.setTotalCounts(totalCounts);
    }
}
