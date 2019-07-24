package cn.itcast.travel.service.category;

import cn.itcast.travel.dao.category.CategoryDao;
import cn.itcast.travel.dao.category.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.utils.JedisUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao  = new CategoryDaoImpl();
    @Override
    public String findAll() {
        // redis
        String jsonString =null;
        Jedis jedis = JedisUtils.getJedis();
        jsonString = jedis.get("heima41_categoryList");//  从redis获取数据
        if(StringUtils.isBlank(jsonString)){
            //  redis没有改数据
           List<Category> list =  categoryDao.findAll();// 查询数据库
            jsonString = JSON.toJSONString(list);
            jedis.set("heima41_categoryList",jsonString);//  存放到redis中
        }
        JedisUtils.close(jedis);
        return jsonString;
    }
}
