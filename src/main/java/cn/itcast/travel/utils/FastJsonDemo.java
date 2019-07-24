package cn.itcast.travel.utils;

import cn.itcast.travel.domain.User;
import com.alibaba.fastjson.JSON;

public class FastJsonDemo {
    public static void main(String[] args) {
        User user= new User();
        user.setSex("男");
        user.setName("lisi");
        user.setTelephone("138");
        user.setEmail("hehe@163.com");
        System.out.println(JSON.toJSONString(user));
        // {"email":"hehe@163.com","name":"lisi","sex":"男","telephone":"138","uid":0}
    }
}
