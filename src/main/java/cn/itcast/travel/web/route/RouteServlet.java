package cn.itcast.travel.web.route;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.route.RouteService;
import cn.itcast.travel.service.route.RouteServiceImpl;
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
import java.util.List;
import java.util.Map;

@WebServlet("/routeServlet")
public class RouteServlet extends BaseServlet {

    /**
     * 详情查询
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findRouteDetailByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        //  Route
        RouteService routeService = new RouteServiceImpl();
        //  将线路多表查询的结果集封装Route对象即可
        Route route = routeService.findRouteDetailByRid(rid);

        String toJSONString = JSON.toJSONString(route);

        return "/json"+toJSONString;

    }

//  各司其职
    public String list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Map<String,Object> map = new HashMap<String,Object>();//  装载分页查询数据  List<T>   int[] pageBar
        String page= request.getParameter("page");//  第二次分页查询
        String pageSize = request.getParameter("pageSize");
        if(StringUtils.isBlank(page)&&StringUtils.isBlank(pageSize)){
            // 第一次分页查询  初始化页码和每页显示记录数
            page="1";
            pageSize="10";
        }
        RouteService routeService = new RouteServiceImpl();
        routeService.pageQuery(map,cid,Integer.parseInt(page),Integer.parseInt(pageSize));
        String toJSONString = JSON.toJSONString(map);//  {key:[{},{},{},key:int[]]}

        return "/json"+toJSONString;

    }
}
