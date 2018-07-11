package com.zbiti.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slb on 2018/4/23.
 */

@WebServlet(
        name = "CustomerServlet",
        urlPatterns = {"/customer", "/editCustomer", "/updateCustomer"}
)
public class CustomerServlet extends HttpServlet {
    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public void init() throws ServletException {
        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setName("A");
        customer1.setCity("haikou");
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setName("B");
        customer2.setCity("sanya");
        customers.add(customer2);

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setName("C");
        customer3.setCity("wenchang");
        customers.add(customer3);
    }

    private void sendCustomerList(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<html><head><tittle>Customer</tittle></head>" +
                "<body><h2>Customers</h2>");
        printWriter.println("<ul>");
        for (Customer customer : customers) {
            printWriter.println("<li>" +
                    customer.getName() +
                    "(" + customer.getCity() + ")(" +
                    "<a href = 'editCustomer?id=" + customer.getId() + "'>edit</a>)");
        }
        printWriter.println("</ul>");
        printWriter.println("</body></html>");

    }

    private Customer getCustomer(int customerId) {
        for(Customer customer : customers){
            if(customer.getId()==customerId){
                return customer;
            }
        }

        return null;
    }

    private void sendEditCustomerForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        int customerId = 0;
        try {
            customerId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }

         Customer customer = this.getCustomer(customerId);

        if(customer != null){
            printWriter.println("<html><head>"
                    +"<tittle>Edit Customer</tittle>"
                    + "<body><h2>Edit Customer</h2>"
                    +"<form method='post' action='updateCustomer'>"
                    +"<input type='hidden' name='id' value='"+customerId+"'/>");
            printWriter.println("<table>"
                    +"<tr><td>Name:</td><td>"
                    +"<input name='name' value='"+customer.getName().replaceAll("'","$#39;")+"'/>"
                    +"</td></tr>");
            printWriter.println("<table>"
                        +"<tr><td>City:</td><td>"
                    +"<input name='city' value='"+customer.getCity().replaceAll("'","$#39;")+"'/>"
                    +"</td></tr>");
            printWriter.println("<tr>"
                    +"<td colspan='2' style='text-align:right'>"
                    +"<input type='submit' value='Update'/>"
                    +"</td></tr>");
            printWriter.println("<tr><td colspan='2'>"
                    +"<a href='customer'>Customer List</a>"
                    +"</td></tr>");
            printWriter.println("</table></form></body>");
        }else{
            printWriter.println("no customer found");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/customer")) {
            sendCustomerList(resp);
        } else if (uri.endsWith("editCustomer")) {
            sendEditCustomerForm(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //update customer
        int customerId = 0;
        try {
            customerId = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {

        }

        Customer customer = this.getCustomer(customerId);
        if (customer != null) {
            customer.setName(req.getParameter("name"));
            customer.setCity(req.getParameter("city"));

        }
        sendCustomerList(resp);
    }
}
