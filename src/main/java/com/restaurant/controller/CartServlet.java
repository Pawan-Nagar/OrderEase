package com.restaurant.controller;

import com.restaurant.dao.MenuItemDAO;
import com.restaurant.model.MenuItem;
import com.restaurant.model.OrderDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, OrderDetail> cart = (Map<Integer, OrderDetail>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        int menuItemId = Integer.parseInt(request.getParameter("menuItemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String specialInstructions = request.getParameter("specialInstructions");

       OrderDetail orderDetail = new OrderDetail();
       orderDetail.setMenuItem(new MenuItemDAO().getMenuItemById(menuItemId));
       orderDetail.setQuantity(quantity);
       orderDetail.setSpecialInstructions(specialInstructions);
        cart.put(menuItemId, orderDetail);

        session.setAttribute("cart", cart);
        response.sendRedirect("menu");
    }
}
