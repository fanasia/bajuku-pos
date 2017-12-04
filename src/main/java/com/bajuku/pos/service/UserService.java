package com.bajuku.pos.service;

import com.bajuku.pos.model.UserModel;
import com.bajuku.pos.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/user/*")
public class UserService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private UserRepository repository=new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            String jsonString= mapper.writeValueAsString(repository.getAllUser());
            resp.getWriter().write(jsonString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel model= mapper.readValue(req.getParameter("model"),UserModel.class);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            repository.deleteUser(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
