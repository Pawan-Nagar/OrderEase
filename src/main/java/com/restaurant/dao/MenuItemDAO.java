package com.restaurant.dao;

import com.restaurant.model.MenuItem;
import com.restaurant.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuItemDAO {
	   public static Map<String, ArrayList<MenuItem>> showMenu() {
	        Map<String, ArrayList<MenuItem>> menuMap = new HashMap<>();
	        try {
	            Connection con = DBConnection.getConnection();
	            String query = "SELECT * FROM menu_items";
	            PreparedStatement ps = con.prepareStatement(query);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	            	MenuItem menu = new MenuItem();
	            	menu.setId(rs.getInt("id"));
	            	menu.setName(rs.getString("name"));
	            	menu.setPrice(rs.getInt("price"));
	            	menu.setDescription(rs.getString("description"));
	            	menu.setCategory(rs.getString("category"));
	                String category = rs.getString("category");
	                menuMap.computeIfAbsent(category, k -> new ArrayList<>()).add(menu);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return menuMap;
	    }
	   public List<MenuItem> getAllMenuItems() {
	        List<MenuItem> menuItems = new ArrayList<>();
	        try (Connection connection = DBConnection.getConnection();
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery("SELECT * FROM menu_items")) {
	            while (resultSet.next()) {
	                MenuItem item = new MenuItem();
	                item.setId(resultSet.getInt("id"));
	                item.setName(resultSet.getString("name"));
	                item.setCategory(resultSet.getString("category"));
	                item.setDescription(resultSet.getString("description"));
	                item.setPrice(resultSet.getDouble("price"));
	                menuItems.add(item);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return menuItems;
	    }
    public MenuItem getMenuItemById(int id) {
        MenuItem item = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM menu_items WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    item = new MenuItem();
                    item.setId(resultSet.getInt("id"));
                    item.setName(resultSet.getString("name"));
                    item.setCategory(resultSet.getString("category"));
                    item.setDescription(resultSet.getString("description"));
                    item.setPrice(resultSet.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void addMenuItem(MenuItem menuItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO menu_items (name, category, description, price) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getCategory());
            preparedStatement.setString(3, menuItem.getDescription());
            preparedStatement.setDouble(4, menuItem.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE menu_items SET name = ?, category = ?, description = ?, price = ? WHERE id = ?")) {
            preparedStatement.setString(1, menuItem.getName());
            preparedStatement.setString(2, menuItem.getCategory());
            preparedStatement.setString(3, menuItem.getDescription());
            preparedStatement.setDouble(4, menuItem.getPrice());
            preparedStatement.setInt(5, menuItem.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteMenuItem(int id) {
    	  Connection connection = null;
          PreparedStatement deleteOrderDetailsStmt = null;
          PreparedStatement deleteMenuItemStmt = null;

          try {
              connection = DBConnection.getConnection();
              connection.setAutoCommit(false); 

              String deleteOrderDetailsQuery = "DELETE FROM order_details WHERE menu_item_id = ?";
              deleteOrderDetailsStmt = connection.prepareStatement(deleteOrderDetailsQuery);
              deleteOrderDetailsStmt.setInt(1, id);
              deleteOrderDetailsStmt.executeUpdate();

              String deleteMenuItemQuery = "DELETE FROM menu_items WHERE id = ?";
              deleteMenuItemStmt = connection.prepareStatement(deleteMenuItemQuery);
              deleteMenuItemStmt.setInt(1, id);
              int rowsAffected = deleteMenuItemStmt.executeUpdate();

              connection.commit();

              return rowsAffected > 0;

          } catch (SQLException e) {
              if (connection != null) {
                  try {
                      connection.rollback(); // Rollback transaction on error
                  } catch (SQLException ex) {
                      ex.printStackTrace();
                  }
              }
              e.printStackTrace();
              return false;
          }
    }
}
