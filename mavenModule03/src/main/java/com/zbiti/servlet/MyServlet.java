package com.zbiti.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by slb on 2018/4/20.
 */

@WebServlet(name="MyServlet", urlPatterns={"/my"})
public class MyServlet implements Servlet{

    private  transient  ServletConfig servletConfig;
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;

    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        String servletName = servletConfig.getServletName();
        servletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = servletResponse.getWriter();
        printWriter.print("<html><head></head>"+"<body>Hello from  "+servletName+"</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "My Servlet";
    }

    @Override
    public void destroy() {

    }
}
