package cn.itcast.travel.web.favorite;

import cn.itcast.travel.dao.routeimg.RouteImgDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Pagination;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.favorite.FavoriteService;
import cn.itcast.travel.service.favorite.FavoriteServiceImpl;
import cn.itcast.travel.service.route.RouteService;
import cn.itcast.travel.service.route.RouteServiceImpl;
import cn.itcast.travel.utils.Myfactory;
import cn.itcast.travel.web.base.BaseServlet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/favoriteServlet")
public class FavoriteServlet extends BaseServlet {


    public String mylistfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      User existUser = (User)request.getSession().getAttribute("existUser");
      if(existUser==null){
          response.getWriter().print("0");
          return "";
      }else{
          String page= request.getParameter("page");//  第二次分页查询
          String pageSize = request.getParameter("pageSize");
          Pagination<Route> pagination = new Pagination<Route>();
          if(StringUtils.isNotBlank(page)&&StringUtils.isNotBlank(pageSize)){
              // 第2次分页查询  初始化页码和每页显示记录数
              pagination.setPage(Integer.parseInt(page));
              pagination.setPageSize(Integer.parseInt(pageSize));
          }
          FavoriteService favoriteService =  Myfactory.getInstance(FavoriteService.class);
          favoriteService.pageQuery(pagination,existUser.getUid());
          String toJSONString = JSON.toJSONString(pagination);
          //  JSON.toJSONString {key:Value}  key : page:  pagination.getPage()
          return "/json"+toJSONString;

      }



    }
    public String addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String,Object> map = new HashMap<String,Object>();
        String rid = request.getParameter("rid");
        User existUser = (User) request.getSession().getAttribute("existUser");
        if(existUser==null){
            map.put("flag","0");
        }else{
            //  实现收藏功能  查询线路被收藏的最新次数
//            FavoriteService favoriteService = new FavoriteServiceImpl();
            FavoriteService favoriteService =  Myfactory.getInstance(FavoriteService.class);
            favoriteService.addFavorite(rid,existUser.getUid());//  业务层  事务管理
            //  favorite  insert  into .values(udi ,rid ,date)...     route  update  count +1  where  rid = ?
            RouteService routeService = new RouteServiceImpl();
            Route route = routeService.findRouteByRid(rid);// Route route =  select * from tab_route where rid = ?
            //  select count from tab_route where rid = ?
             map.put("count",route.getCount());
        }

        String toJSONString = JSON.toJSONString(map);
        return "/json"+toJSONString;

    }

        public void isnotFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");

        User existUser = (User) request.getSession().getAttribute("existUser");
        if(existUser!=null){
            //  判断用户有无收藏
           FavoriteService favoriteService = new FavoriteServiceImpl();
           Favorite favorite =  favoriteService.isnotFavorite(rid,existUser.getUid());
           if(favorite!=null){
               response.getWriter().print("2");
           }
        }

    }

}
