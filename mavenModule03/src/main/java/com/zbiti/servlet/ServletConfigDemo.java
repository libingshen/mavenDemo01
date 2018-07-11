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
        name = "ServletConfigDemo",
        urlPatterns = "/servletConfigDemo",
        initParams = {
                @WebInitParam(
                        name = "admin",
                        value = "Harry"
                ),
                @WebInitParam(
                        name = "email",
                        value = "admin@example.com"
                )
        }

)
public class ServletConfigDemo implements Servlet {
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
        servletResponse.setContentType("text/html;charset=utf-8");
        ServletConfig servletConfig = this.getServletConfig();
        String admin = servletConfig.getInitParameter("admin");
        String email = servletConfig.getInitParameter("email");

        PrintWriter printWriter = servletResponse.getWriter();
        printWriter.print("<html><head></head><body>"+"Admin:"+admin+"</br>Email:"+email+"</body></html>");

    }

    @Override
    public String getServletInfo() {
        return "ServletConfig Demo";
    }

    @Override
    public void destroy() {

    }
}
