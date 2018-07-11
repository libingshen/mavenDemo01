package com.zbiti.servlet;


import javafx.beans.binding.NumberExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slb on 2018/4/23.
 */
/**

 * @Description:    网址重写：一种session追踪技术

 * @Author:         slb

 * @CreateDate:     2018/4/23 11:17

 */

@WebServlet(
        name = "Top10Servlet",
        urlPatterns = {"/top10"}
)
public class Top10Servlet extends HttpServlet {
    private List<String> londonAttractions;
    private List<String> prarisAttractions;

    @Override
    public void init() throws ServletException {
        londonAttractions = new ArrayList<String>(10);
        londonAttractions.add("A1");
        londonAttractions.add("A2");
        londonAttractions.add("A3");
        londonAttractions.add("A4");
        londonAttractions.add("A5");
        londonAttractions.add("A6");
        londonAttractions.add("A7");
        londonAttractions.add("A8");
        londonAttractions.add("A9");
        londonAttractions.add("A10");

        prarisAttractions = new ArrayList<String>(10);
        prarisAttractions.add("B1");
        prarisAttractions.add("B2");
        prarisAttractions.add("B3");
        prarisAttractions.add("B4");
        prarisAttractions.add("B5");
        prarisAttractions.add("B6");
        prarisAttractions.add("B7");
        prarisAttractions.add("B8");
        prarisAttractions.add("B9");
        prarisAttractions.add("B10");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        if (city != null && (city.equals("london") || city.equals("paris"))) {
            showAttractions(req, resp, city);
        } else showMainPage(req, resp);
    }

    private void showMainPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.print("<html><head>" +
                        "<tittle>Top 10 Tourist Attractions</tittle>" +
                        "</head><body>" +
                        "Please select a city:" +
                        "<br/><a href='?city=london'>London</a>" +
                        "<br/><a href='?city=paris'>Paris</a>" +
                        "</body></html>");

    }

    private void showAttractions(HttpServletRequest req, HttpServletResponse resp, String city) throws IOException {
        int page = 1;
        String pageParameter = req.getParameter("page");
        if(pageParameter != null){
            try{
                page = Integer.parseInt(pageParameter);
            }catch (NumberFormatException e){

            }
            if(page>2){
                page = 1;
            }
        }

        List<String>  attractions = null;
        if(city.equals("london")){
            attractions = londonAttractions;
        }else if(city.equals("paris")){
            attractions = prarisAttractions;
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html><head>"+
                "<tittle>Top 10 Tourist Attractions</tittle>"+
                "</head><body>");
        printWriter.println("<a href = 'top10'>Select City</a>");
        printWriter.println("<hr/>Page"+page+"<hr/>");
        int start = page*5 - 5;
        for(int i = start ;i<start + 5 ;i++){
            printWriter.println(attractions.get(i)+"<br/>");
        }
        printWriter.println("<hr style='color:blue'/>"+
                "<a href = '?city="+city+"&page=1'>Page 1</a>");
        printWriter.println("&nbsp;<a href = '?city="+city+"&page=2'>Page 2</a>");
        printWriter.println("</body></html>");
    }

}
