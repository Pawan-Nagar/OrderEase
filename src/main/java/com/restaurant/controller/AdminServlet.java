package com.restaurant.controller;

import com.restaurant.dao.MenuItemDAO;
import com.restaurant.dao.OrderDAO;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private MenuItemDAO menuItemDAO;

    public void init() {
        menuItemDAO = new MenuItemDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();
        request.setAttribute("menuItems", menuItems);
        List<Order> orders = OrderDAO.getAllOrdersWithDetails();
        System.out.println(orders.size());
        request.setAttribute("orders", orders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            MenuItem menuItem = new MenuItem();
            menuItem.setName(name);
            menuItem.setCategory(category);
            menuItem.setDescription(description);
            menuItem.setPrice(price);

            menuItemDAO.addMenuItem(menuItem);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));

            MenuItem menuItem = new MenuItem();
            menuItem.setId(id);
            menuItem.setName(name);
            menuItem.setCategory(category);
            menuItem.setDescription(description);
            menuItem.setPrice(price);

            menuItemDAO.updateMenuItem(menuItem);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            menuItemDAO.deleteMenuItem(id);
        }
        response.sendRedirect("admin");
    }
}
