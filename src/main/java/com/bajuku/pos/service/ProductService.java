package com.bajuku.pos.service;

import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.repository.ProductRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/product/*")
public class ProductService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private ProductRepository repository= new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String jsonString= mapper.writeValueAsString(repository.getAllProduct());
            resp.getWriter().write(jsonString);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductModel model= mapper.readValue(req.getParameter("data"), ProductModel.class);

    }
}
