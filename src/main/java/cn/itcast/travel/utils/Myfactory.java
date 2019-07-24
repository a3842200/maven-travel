package cn.itcast.travel.utils;

import cn.itcast.travel.dao.route.RouteDao;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 工厂类  作用：
 * 根据applicationContext.xml 配置 获取接口指定实现类
 *
 * ioc 控制反转   对象创建权利 交给 Spring工厂  根据配置文件   aop
 */
public class Myfactory {
    //  UserDao.class---> USerDaoImpl    USerService.class    RouteDao.class
    public static <T>  T  getInstance(Class<T> c) {
        try {
            String simpleName = c.getSimpleName();//  字节码对应的类名  RouteDao
            SAXReader reader = new SAXReader();
            String url=new ParseXMLDemo().getClass().getClassLoader().getResource("applicationContext.xml").getPath();
            Document document = reader.read(url);// 解析xml配置文件
            // 传递 id 对应值 找到包名.类名
//            <bean id="RouteDao" class="cn.itcast.travel.dao.route.RouteDaoImpl"></bean>
            Element node = (Element) document.selectSingleNode("//bean[@id='"+simpleName+"']");
            Attribute attributeValue = node.attribute("class");//  获取元素的class属性对象
            Object obj = Class.forName(attributeValue.getValue()).newInstance();// 根据配置获取指定的实现类
            return (T)obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getCause());
        }

    }
}
