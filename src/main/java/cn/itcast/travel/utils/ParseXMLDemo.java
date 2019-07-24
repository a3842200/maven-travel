package cn.itcast.travel.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class ParseXMLDemo {

    public static void main(String[] args) {
        //  Dom4j  dom for java   + xpath
        //  解析  类路径 applicationContext.xml
        try {
            SAXReader reader = new SAXReader();
            String url=new ParseXMLDemo().getClass().getClassLoader().getResource("applicationContext.xml").getPath();
            Document document = reader.read(url);
           // List<Element> list = document.selectNodes("//bean");
//            System.out.println(list.size());

            Element node = (Element) document.selectSingleNode("//bean[@id='FavoriteDao']");
            Attribute aClass = node.attribute("class");
            
            System.out.println(aClass.getValue());

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
