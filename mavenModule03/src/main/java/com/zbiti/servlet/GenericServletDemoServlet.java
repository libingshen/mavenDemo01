package com.zbiti.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by slb on 2018/4/20.
 */

@WebServlet(
        name = "GenericServletDemoServlet",
        urlPatterns = {"/generic"},
        initParams = {
                @WebInitParam(
                        name = "admin",
                        value = "xiaobingbing"
                ),
                @WebInitParam(
                        name = "email",
                        value = "782125244@qq.com"
                )
        }
)
public class GenericServletDemoServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        ServletConfig servletConfig = this.getServletConfig();
        String admin = servletConfig.getInitParameter("admin");
        String email = servletConfig.getInitParameter("email");
        servletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = servletResponse.getWriter();
        printWriter.print("<html><head></head><body>"+"Admin:"+admin+"</br>Email:"+email+"</body></html>");
    }
}
