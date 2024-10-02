package com.restaurant.controller;

import com.restaurant.dao.OrderDAO;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderDetail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutServlet extends HttpServlet {
    private OrderDAO orderDAO;

    public void init() {
        orderDAO = new OrderDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, OrderDetail> cart = (Map<Integer, OrderDetail>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        String customerName = (String) session.getAttribute("customer");
        String customerEmail = (String) session.getAttribute("customerEmail");

//        if (customerName == null || customerName.isEmpty()) {
//            customerName = "Guest";
//            customerEmail = "guest@example.com"; 
//        }
        

        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));

        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setTotalAmount(totalAmount);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetail item : cart.values()) {
            orderDetails.add(item);
        }
        order.setOrderDetails(orderDetails);

        int orderId = orderDAO.saveOrder(order);
        if (orderId > 0) {
            session.removeAttribute("cart");
            request.setAttribute("orderId", orderId);
            request.setAttribute("order", order);
            RequestDispatcher dispatcher = request.getRequestDispatcher("checkout.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("cart.jsp");
        }
    }
}
