package com.bajuku.pos.service;

import com.bajuku.pos.repository.LogRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/api/log/*")
public class LogService extends HttpServlet {
    private ObjectMapper mapper= new ObjectMapper();
    private LogRepository repository= new LogRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String array=null;
        int count=0;

        if(req.getParameter("search-log")!=null){
            try {
                SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date temp= format.parse(req.getParameter("search-log")+" 00:00:00");
                Timestamp begin= new java.sql.Timestamp(temp.getTime());

                temp= format.parse(req.getParameter("search-log")+" 23:59:59");
                Timestamp end= new java.sql.Timestamp(temp.getTime());
                System.out.println(begin+" "+end);

                array= mapper.writeValueAsString(repository.getSearchLog(begin, end, Integer.parseInt(req.getParameter("page"))));
                count= repository.getLogCount(begin, end);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                array = mapper.writeValueAsString(repository.getAllLog(Integer.parseInt(req.getParameter("page"))));
                count = repository.getLogCount(null, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        resp.getWriter().write("{\"array\" : "+array+", \"count\" : "+count+"}");
    }
}
