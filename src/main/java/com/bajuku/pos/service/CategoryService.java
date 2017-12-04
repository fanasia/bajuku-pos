package com.bajuku.pos.service;

import com.bajuku.pos.model.CategoryModel;
import com.bajuku.pos.repository.CategoryRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        try {
            String jsonString= mapper.writeValueAsString(repository.getAllCategories());
            resp.getWriter().write(jsonString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryModel model= mapper.readValue(req.getParameter("data"), CategoryModel.class);
        PrintWriter out= resp.getWriter();

        try {
            out.print(repository.insertCategory(model));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
