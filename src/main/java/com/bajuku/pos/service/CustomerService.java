package com.bajuku.pos.service;

import com.bajuku.pos.model.CustomerModel;
import com.bajuku.pos.repository.CustomerRepository;
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

@WebServlet("/api/customer/*")
public class CustomerService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private CustomerRepository repository=new CustomerRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String array=null;
        int count=0;

        if(req.getParameter("search-customer")!=null){
            try {
                array= mapper.writeValueAsString(repository.getSearchCustomer(
                        req.getParameter("search-customer").toLowerCase(),
                        Integer.parseInt(req.getParameter("page"))));
                count= repository.getCountCustomer(req.getParameter("search-customer"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                array = mapper.writeValueAsString(repository.getAllCustomer(
                        Integer.parseInt(req.getParameter("page"))));
                count = repository.getCountCustomer(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write("{\"array\": "+array+", \"count\": "+count+"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CustomerModel model= mapper.readValue(req.getParameter("data"),CustomerModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.insertCustomer(model,
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.deleteCustomer(Integer.parseInt(req.getParameter("id")),
                    Integer.parseInt(session.getAttribute("id").toString())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader= req.getReader();
        String json= reader.readLine();
        System.out.println(json);

        CustomerModel model= mapper.readValue(json,CustomerModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.updateCustomer(model,
                    Integer.parseInt(session.getAttribute("id").toString())
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
