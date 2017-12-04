package com.bajuku.pos.service;

import com.bajuku.pos.repository.LogRepository;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogService extends HttpServlet {
    private ObjectMapper mapper= new ObjectMapper();
    private LogRepository repository= new LogRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
