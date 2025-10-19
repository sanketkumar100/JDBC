package org.example;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SecondServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String name = (String) req.getAttribute("user");
        out.println("<h2>Hello " + name + ", you are now in SecondServlet!</h2>");
    }
}
