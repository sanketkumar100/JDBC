package org.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        req.setAttribute("user", username);  // sending data to next servlet

        RequestDispatcher rd = req.getRequestDispatcher("second");
        rd.forward(req, res);  // transfer control to SecondServlet
    }
}

