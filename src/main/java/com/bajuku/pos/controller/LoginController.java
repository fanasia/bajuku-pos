package com.bajuku.pos.controller;

import com.bajuku.pos.model.UserModel;
import com.bajuku.pos.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserRepository userRepository=new UserRepository();
    private UserModel model=new UserModel();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session= req.getSession();

        //clear if session is already created
        if(session!=null){
            session.removeAttribute("user");
            session.removeAttribute("username");
            session.removeAttribute("role");
        }

        String username= req.getParameter("username");
        String password= req.getParameter("password");

        try {
            model= userRepository.authenticateUser(username,password);

            if (model.getFullname()==null){
                req.setAttribute("error", "Wrong username or password");
                req.getRequestDispatcher("/").forward(req, resp);
            }
            else {
                session.setAttribute("user", model.getFullname());
                session.setAttribute("username", model.getUsername());
                session.setAttribute("role", model.getUser_role());
                if(model.getUser_role().equals("admin"))
                    resp.sendRedirect("admin.jsp");
                else
                    resp.sendRedirect("cashier.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error","Server failure");
            req.getRequestDispatcher("/").forward(req, resp);
        }

    }
}
