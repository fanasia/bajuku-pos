package com.bajuku.pos.service;

import com.bajuku.pos.model.UserModel;
import com.bajuku.pos.repository.UserRepository;
import org.codehaus.jackson.impl.DefaultPrettyPrinter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/api/user/*")
public class UserService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private UserRepository repository=new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if(req.getParameter("search-user")!=null||req.getParameter("select-role")!=null){
            try {
                String array= mapper.writeValueAsString(repository.getSearchUser(
                        req.getParameter("select-role"),req.getParameter("search-user").toLowerCase(),
                        Integer.parseInt(req.getParameter("page"))));
                int count= repository.getCountUser(req.getParameter("select-role"),req.getParameter("search-user"));

                resp.getWriter().write("{\"count\": "+count+", \"array\": "+array+"}");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repository.getAllUser());
                resp.getWriter().write(jsonString);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel model= mapper.readValue(req.getParameter("data"),UserModel.class);
        PrintWriter out= resp.getWriter();
        try {
            out.print(repository.insertUser(model));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader= req.getReader();
        String json= reader.readLine();

        UserModel model= mapper.readValue(json, UserModel.class);
        PrintWriter out= resp.getWriter();
        try {
            out.print(repository.updateUser(model));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        try {
            out.print(repository.deleteUser(Integer.parseInt(req.getParameter("id"))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
