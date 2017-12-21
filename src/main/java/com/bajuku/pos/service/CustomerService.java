package com.bajuku.pos.service;

import com.bajuku.pos.model.CustomerModel;
import com.bajuku.pos.repository.CustomerRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/customer/*")
public class CustomerService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private CustomerRepository repository=new CustomerRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if(req.getParameter("search-customer")!=null){
            try {
                String array= mapper.writeValueAsString(repository.getSearchCustomer(
                        req.getParameter("search-customer").toLowerCase(),
                        Integer.parseInt(req.getParameter("page"))));
                int count= repository.getCountCustomer(req.getParameter("search-user"));

                resp.getWriter().write("{\"count\": "+count+", \"array\": "+array+"}");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String jsonString = mapper.writeValueAsString(repository.getAllCustomer());
                resp.getWriter().write(jsonString);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerModel model= mapper.readValue(req.getParameter("data"),CustomerModel.class);
        PrintWriter out= resp.getWriter();
        try {
            out.print(repository.insertCustomer(model));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerModel model= mapper.readValue(req.getParameter("data"),CustomerModel.class);
        PrintWriter out= resp.getWriter();
        try {
            out.print(repository.updateCustomer(model));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            repository.deleteCustomer(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
