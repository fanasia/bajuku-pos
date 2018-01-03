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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        String array=null;
        int count=0;

        SimpleDateFormat parser= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            if(req.getParameter("id-details")!=null){
                array= mapper.writeValueAsString(transaction.getObjectDetails(Integer.parseInt(req.getParameter("id-details"))));
                System.out.println(array);
            }
            //request transaction in a day
            else if(req.getParameter("view-date")!=null){
                try {
                    System.out.println(parser.parse(req.getParameter("view-date")+" 00:00:00"));
                    System.out.println(format.format(parser.parse(req.getParameter("view-date")+" 00:00:00")));
                    array = mapper.writeValueAsString(transaction.viewDailyTransactions(
                            new Timestamp(parser.parse(req.getParameter("view-date")+" 00:00:00").getTime())
                    ));

                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
            else if(req.getParameter("search-ledger")!=null){
                array= mapper.writeValueAsString(transaction.getSearchTransaction(
                        req.getParameter("interval"),
                        req.getParameter("search-ledger"),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= transaction.getCountTransaction(req.getParameter("interval"),
                        req.getParameter("search-ledger"));
            }
            else {
                array = mapper.writeValueAsString(transaction.getAllTransactions(
                        req.getParameter("interval"),
                        Integer.parseInt(req.getParameter("page"))
                ));
                count= transaction.getCountTransaction(req.getParameter("interval"), null);
            }

            resp.getWriter().write("{\"array\" : "+array+", \"count\" : "+count+"}");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session= req.getSession(false);
        PrintWriter out= resp.getWriter();
        try {
            TypeReference<List<Integer>> type= new TypeReference<List<Integer>>(){};
            List<Integer> id= mapper.readValue(req.getParameter("id"), type);
            List<Integer> quantity= mapper.readValue(req.getParameter("quantity"), type);
            ArrayList<ProductModel> cart= repository.findById(id);

            System.out.println(req.getParameter("discount")+ req.getParameter("points"));
            out.print(transaction.insertTransaction(cart, quantity,
                    req.getParameter("customer_id"),
                    req.getParameter("discount"),
                    req.getParameter("points"),
                    Integer.parseInt(session.getAttribute("id").toString())
            ));

            System.out.println("finished");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
