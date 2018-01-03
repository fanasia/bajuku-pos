package com.bajuku.pos.service;

import com.bajuku.pos.model.UserModel;
import com.bajuku.pos.repository.UserRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.impl.DefaultPrettyPrinter;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

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

@WebServlet("/api/user/*")
public class UserService extends HttpServlet{
    private ObjectMapper mapper= new ObjectMapper();
    private UserRepository repository=new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String array=null;
        int count=0;

        if(req.getParameter("search-user")!=null||req.getParameter("select-role")!=null){
            try {
                array= mapper.writeValueAsString(repository.getSearchUser(
                        req.getParameter("select-role"),
                        req.getParameter("search-user").toLowerCase(),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= repository.getCountUser(req.getParameter("select-role"),req.getParameter("search-user"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                array = mapper.writerWithDefaultPrettyPrinter().
                        writeValueAsString(repository.getAllUser(
                                Integer.parseInt(req.getParameter("page"))
                        ));
                count= repository.getCountUser(null, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write("{\"array\": "+array+", \"count\": "+count+"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel model= mapper.readValue(req.getParameter("data"),UserModel.class);
        PrintWriter out= resp.getWriter();
        HttpSession session= req.getSession(false);
        try {
            out.print(repository.insertUser(model,
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
            out.print(repository.deleteUser(Integer.parseInt(req.getParameter("id")),
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
        System.out.println(json);

        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        if(req.getRequestURI().contains("/password")){
            try {
                JsonNode jsonNode= mapper.readTree(json);
                out.print(repository.changePassword(
                        jsonNode.get("password").asText(),
                        jsonNode.get("id").asInt(),
                        Integer.parseInt(session.getAttribute("id").toString())
                        ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            UserModel model = mapper.readValue(json, UserModel.class);
            try {
                out.print(repository.updateUser(model,
                        Integer.parseInt(session.getAttribute("id").toString())
                ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
