package com.bajuku.pos.service;

import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.repository.ProductRepository;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

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
import java.util.List;

@WebServlet("/api/product/*")
public class ProductService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private ProductRepository repository= new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String array=null;
        int count=0;

        if(req.getParameter("search-product")!=null||req.getParameter("select-categories")!=null){
            try {
                array= mapper.writeValueAsString(repository.getSearchProduct(
                        req.getParameter("select-categories"), req.getParameter("search-product"),
                        Integer.parseInt(req.getParameter("limit")),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= repository.getCountProduct(req.getParameter("select-categories"), req.getParameter("search-product"));
                resp.getWriter().write("{\"array\" : "+array+", \"count\" : "+count+"}");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(req.getRequestURI().endsWith("/getById")){
            try {
                List<Integer> list= mapper.readValue(req.getParameter("id"), new TypeReference<List<Integer>>(){});
                String cartList= mapper.writeValueAsString(repository.findById(list));
                resp.getWriter().write(cartList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                array = mapper.writeValueAsString(repository.getAllProduct(
                        Integer.parseInt(req.getParameter("limit")),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= repository.getCountProduct(null,null);
                resp.getWriter().write("{\"array\" : "+array+", \"count\" : "+count+"}");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductModel model= mapper.readValue(req.getParameter("data"), ProductModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.insertProduct(model,
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.deleteProduct(Integer.parseInt(req.getParameter("id")),
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader= req.getReader();
        String json= reader.readLine();

        ProductModel model= mapper.readValue(json,ProductModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.updateProduct(model,
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

