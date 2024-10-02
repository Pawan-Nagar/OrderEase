package com.restaurant.controller;

import com.restaurant.dao.CustomerDAO;
import com.restaurant.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
       	  HttpSession oldSession = request.getSession(false);
             if (oldSession != null) {
                 oldSession.invalidate();
             }

             HttpSession session = request.getSession(true);
             session.setAttribute("customer", "Guest");
             session.setAttribute("customerEmail","Guest@gmail.com");

             RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
             dispatcher.forward(request, response);
             
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 HttpSession oldSession = request.getSession(false);
         if (oldSession != null) {
             oldSession.invalidate();
         }

         HttpSession session = request.getSession(true);
         
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Customer customer = customerDAO.getCustomerByEmail(email);

        if (customer != null && customer.getPassword().equals(password)) {
        	 
              
            session.setAttribute("customer", customer.getName());
            session.setAttribute("customerEmail",email);
            
            if ("admin".equals(customer.getRole())) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid email or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
