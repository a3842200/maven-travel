package cn.itcast.travel.web.user;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.user.UserService;
import cn.itcast.travel.service.user.UserServiceImpl;
import cn.itcast.travel.utils.CodeUtils;
import cn.itcast.travel.utils.JedisUtils;
import cn.itcast.travel.utils.Myfactory;
import cn.itcast.travel.utils.SendMessage;
import cn.itcast.travel.web.base.BaseServlet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

    /**
     * 用户退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断用户有无登录
       request.getSession().invalidate();
       response.getWriter().print("0");

    }


    /**
     * 判断用户有无登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isnotlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断用户有无登录
        User existUser = (User)request.getSession().getAttribute("existUser");
        if(existUser==null){
            response.getWriter().print("0");
        }else{
            String jsonString = JSON.toJSONString(existUser);
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().print(jsonString);
        }

    }


        /**
         * 用户输入验证码和redis比对
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
    public void validCodeFromRedis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String  telephone = request.getParameter("telephone");
        String  code = request.getParameter("code");

        Jedis jedis = JedisUtils.getJedis();
       String redisCode =  jedis.get(telephone);
       if(StringUtils.isBlank(redisCode)){
           // 过期
           response.getWriter().print("0");
       }else {

           if(code.equalsIgnoreCase(redisCode)){
               //  正确
               response.getWriter().print("2");
           }else{
               // 输入验证码和redis不匹配
               response.getWriter().print("1");
           }
       }


    }

    /**
     * 登录业务发送验证码到手机
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void sendCodeToTelephone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String  telephone = request.getParameter("telephone");
        //  1  用户是否注册
        UserService userService = Myfactory.getInstance(UserService.class);
        // 工厂获取业务层接口实例对象
        User existUser =  userService.findUserByTelephone(telephone);
       if(existUser==null){
           //  手机号没有查询到用户  用户没注册
           response.getWriter().print("2");
       }else{
          //  用户已经注册   发送短信
           try {
               String code = CodeUtils.generateCode();

               Jedis jedis = JedisUtils.getJedis();
               jedis.psetex(telephone,90000L,code);
               JedisUtils.close(jedis);
               System.out.println(code);
//               SendMessage.sendMessage(telephone,code);
               request.getSession().setAttribute("existUser",existUser);//   为后续登录 将用户信息存储到session
               response.getWriter().print("0");
           } catch (Exception e) {
               e.printStackTrace();
               response.getWriter().print("1");
           }

       }








    }

//  tomcat8   get

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
           // String sex = request.getParameter("sex");
            //sex = new String(sex.getBytes("iso-8859-1"),"utf-8");
            User user = new User();
            Map<String, String[]> map = request.getParameterMap();// 表单数据封装Map<String,String[]>
            BeanUtils.populate(user,map);//  apache  BeanUtils 将表单数据直接封装实体Bean中
            //  业务层 + dao
            UserService userService = new UserServiceImpl();
            // userService.register(user);
           //user.setSex(sex);
           response.getWriter().print("0");
        } catch (Exception e) {
            response.getWriter().print("1");
        }


    }

    /**
     * 验证码服务器端校验
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String session_code = (String)request.getSession().getAttribute("code");
        if(code.equalsIgnoreCase(session_code)){
            response.getWriter().print("0");
        } else {
            response.getWriter().print("1");
        }

    }
        /**
     * 校验邮箱的唯一性
     * @param request
     * @param response
     * @return
     */
    public void checkUniqueEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        UserService userService = new UserServiceImpl();
        User existUser = userService.findUserByEmail(email);
        if (existUser == null) {
            response.getWriter().print("0");
        } else {
            response.getWriter().print("1");
        }


    }
//    public String login(HttpServletRequest request, HttpServletResponse response){
//
//
//        UserService  userService = new  UserServiceImpl();
//
//        user.login();
//
//        //  转发  重定向 操作[]  {  }
//
////        response.getWriter().print("json");
//        return "/json+"+jsonString;
//
//
//    }

//    public String register(HttpServletRequest request, HttpServletResponse response){
//
//
//        UserService  userService = new  UserServiceImpl();
//
//        user.register();
//
//
//
//      return "/redlogin.html";
//
//
//    }


}
