package cn.itcast.travel.service.route;

import cn.itcast.travel.dao.route.RouteDao;
import cn.itcast.travel.dao.route.RouteDaoImpl;
import cn.itcast.travel.dao.routeimg.RouteImgDao;
import cn.itcast.travel.dao.routeimg.RouteImgDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.utils.Myfactory;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class RouteServiceImpl implements RouteService {
//    private RouteDao routeDao = new RouteDaoImpl();//  一个dao 操作一张表
    private RouteDao routeDao = Myfactory.getInstance(RouteDao.class);
    private RouteImgDao routeImgDao = Myfactory.getInstance(RouteImgDao.class);
    @Override
    public List<Route> list(String cid) {
        return routeDao.list(cid);
    }

    @Override
    public void pageQuery(Map<String, Object> map, String cid, int page, int pageSize) {

        List<Route> list = routeDao.pageQuery(Integer.parseInt(cid),(page-1)*pageSize,pageSize);
        // select * from tab_route where cid = ? limit ?,?
        int totalCounts = routeDao.queryTotalCounts(Integer.parseInt(cid));
        //  select count(*) from tab_route where cid = ?
        int[] pageBar = generatePageBar(map,page,pageSize,totalCounts);
        map.put("list",list);
        map.put("pageBar",pageBar);
        map.put("totalCounts",totalCounts);
        map.put("page",page);
        map.put("pageSize",pageSize);

    }

    @Override
    public Route findRouteDetailByRid(String rid) {
        //  业务扩展的需求
//        private Category category;//所属分类
//        private Seller seller;//所属商家
//        private List<RouteImg> routeImgList;//商品详情图片列表 List<RouteImg>   select * from tab_route_img where rid = ?
//  Map  map
        Route route = new Route();
        Category category = new Category();
        Seller seller = new Seller();
        //  map  三表查询 结果集数据   jdbcTemplate.queryForMap(sql,rid);
        Map<String,Object> map = routeDao.findRouteDetailByRid(rid);
        //  route  seller  category
        try {
            BeanUtils.populate(route,map);
            BeanUtils.populate(category,map);
            BeanUtils.populate(seller,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
       //  System.out.println(route);
        //  轮播图地址信息查询
        List<RouteImg> routeImgList = routeImgDao.findRouteImageByRid(Integer.parseInt(rid));

         //  相关信息封装Route对象
        route.setCategory(category);
        route.setSeller(seller);
        route.setRouteImgList(routeImgList);

        return route;
    }

    @Override
    public Route findRouteByRid(String rid) {
        Route route = null;
        try{
            return routeDao.findRouteByRid(Integer.parseInt(rid));
        }catch (Exception e){
            e.printStackTrace();
        }
        return route;
    }

    /**
     * 计算分页栏方法实现
     * @param map
     * @param page
     * @param pageSize
     * @param totalCounts
     * @return
     */
    private int[] generatePageBar(Map<String, Object> map, int page, int pageSize, int totalCounts) {
      //  百度分页栏制作 前五后四原则 10个页码    11   19
        int beginPage;  //  10
        int endPage;   //  19
        //  100  10    101   10     99   10
        int totalPages = (totalCounts+pageSize-1)/pageSize;

        if(totalPages<=10){
            beginPage=1;
            endPage=totalPages;
        }else{
            //  大于10个页码 前五后四原则
            beginPage=page-5;
            endPage=page+4;

            //  如果当前页码 page =2  如果 总页码 12页   当前页码 page 11

            if(beginPage<=0){
                beginPage = 1;
                endPage=beginPage+9;
            }

            if(endPage>=totalPages){
                endPage=totalPages;
                beginPage=endPage-9;
            }

        }

    //  数组完成动态分页栏制作

        int arr[] = new int[endPage-beginPage+1];
        int index=0;
        for (int i = beginPage; i <=endPage ; i++) {
            arr[index++]=i;
        }

        map.put("totalPages",totalPages);//  map  装载分页数据

        return arr;
    }
}
