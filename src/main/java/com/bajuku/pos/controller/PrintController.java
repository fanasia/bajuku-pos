package com.bajuku.pos.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.IOException;

@WebServlet("/print/*")
public class PrintController extends HttpServlet{
    private static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }

    private PageFormat getPageFormat(PrinterJob pj){
        PageFormat pf= pj.defaultPage();
        Paper paper= pf.getPaper();

        paper.setSize(convert_CM_To_PPI(8), convert_CM_To_PPI(12));
        paper.setImageableArea(0, 10, convert_CM_To_PPI(7), convert_CM_To_PPI(11));
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(false);
        if(req.getParameter("customer")==null||req.getParameter("customer").equals("undefined"))
            req.setAttribute("customer", "Guest");
        else
            req.setAttribute("customer", req.getParameter("customer"));
        req.setAttribute("payment", req.getParameter("payment"));
        if(req.getParameter("discount")==null){
            req.setAttribute("discount", 0);
        }
        req.setAttribute("discount", req.getParameter("discount"));

        req.getRequestDispatcher("/receipt.jsp").forward(req, resp);
    }
}
