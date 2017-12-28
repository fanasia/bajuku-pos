package com.bajuku.pos.service;

import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.repository.ProductRepository;
import com.bajuku.pos.repository.TransactionRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api/transaction/*")
public class TransactionService extends HttpServlet{
    private ProductRepository repository= new ProductRepository();
    private TransactionRepository transaction= new TransactionRepository();
    private ObjectMapper mapper= new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String jsonString= mapper.writeValueAsString(transaction.getAllTransactions());
            resp.getWriter().write(jsonString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("id")+" "+ req.getParameter("quantity"));
        HttpSession session= req.getSession(false);
        PrintWriter out= resp.getWriter();
        try {
            TypeReference<List<Integer>> type= new TypeReference<List<Integer>>(){};
            List<Integer> id= mapper.readValue(req.getParameter("id"), type);
            List<Integer> quantity= mapper.readValue(req.getParameter("quantity"), type);
            ArrayList<ProductModel> cart= repository.findById(id);

            out.print(transaction.insertTransaction(cart, quantity,
                    req.getParameter("customer_id"),
                    Integer.parseInt(session.getAttribute("id").toString())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
