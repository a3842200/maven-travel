package cn.itcast.travel.web.base;

import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        //  http://xxx/UserServlet?method=login
        //  http://xxx/UserServlet?method=register

        String methodName = request.getParameter("method");

        //  根据method  调用BaseServlet  对应子类UserServlet 对应业务方法

        // UserServlet.class  Class   getMethod  Method  invoke
        //  method  运行servlet  方法对象
        try {
            System.out.println(this);//  实际运行servlet对象
            Method method =this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
              //  method 调用invoke
          String obj =   (String)method.invoke(this,request,response);
          //  commons-lang3
          if(StringUtils.isNotBlank(obj)){
              if(obj.startsWith("/json")){
                  // 如果子类的返回值以/json开头   父类进行json数据的传输  ajax场景使用
                  response.setContentType("text/json;charset=utf-8");
                  //  上述代码服务器告知浏览器 服务器响应数据类型 Json格式的字符串，中文乱码问题
                  response.getWriter().print(obj.substring(5));
              }else if(obj.startsWith("/red")){
                  //  如果子类方法的返回值String以/red开头 父类进行重定向操作
                  response.sendRedirect(obj.substring(4));
              }

          }
            // 子类业务方法如果没有返回值，父类不做处理，此时由子类的方法自行处理响应

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
