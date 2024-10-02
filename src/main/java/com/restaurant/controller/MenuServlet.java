package com.restaurant.controller;

import com.restaurant.dao.MenuItemDAO;
import com.restaurant.model.MenuItem;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MenuServlet extends HttpServlet {
    private MenuItemDAO menuItemDAO;

    public void init() {
        menuItemDAO = new MenuItemDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Map<String, ArrayList<MenuItem>> menuMap = MenuItemDAO.showMenu();
    	request.setAttribute("menuMap", menuMap);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
    	dispatcher.forward(request, response);
    }
}
