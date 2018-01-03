package com.bajuku.pos.service;

import com.bajuku.pos.model.CategoryModel;
import com.bajuku.pos.repository.CategoryRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/categories/*")
public class CategoryService extends HttpServlet {
    private ObjectMapper mapper= new ObjectMapper();
    private CategoryRepository repository= new CategoryRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String array=null;
        int count=0;

        if(req.getParameter("search-categories")!=null){
            System.out.println(req.getParameter("search-categories"));
            try {
                array= mapper.writeValueAsString(repository.getCategorySearch(
                        req.getParameter("search-categories").toLowerCase(),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= repository.getCountCategory(req.getParameter("search-categories"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                array = mapper.writeValueAsString(repository.getAllCategories(
                        Integer.parseInt(req.getParameter("limit")),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= repository.getCountCategory(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write("{\"array\" : "+array+", \"count\" : "+count+"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryModel model= mapper.readValue(req.getParameter("data"), CategoryModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.insertCategory(model,
                    Integer.parseInt(session.getAttribute("id").toString())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.deleteCategory(
                    Integer.parseInt(req.getParameter("id")),
                    Integer.parseInt(session.getAttribute("id").toString())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader= req.getReader();
        String json= reader.readLine();

        CategoryModel model= mapper.readValue(json, CategoryModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.updateCategory(model,
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
