package cn.itcast.travel.web.category;

import cn.itcast.travel.service.category.CategoryService;
import cn.itcast.travel.service.category.CategoryServiceImpl;
import cn.itcast.travel.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseServlet {
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryService categoryService = new CategoryServiceImpl();

        String jsonString = categoryService.findAll();


        return "/json"+jsonString;


    }
}
