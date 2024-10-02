package com.restaurant.dao;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderDetail;
import com.restaurant.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public int saveOrder(Order order) {
        int orderId = 0;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement psOrder = connection.prepareStatement(
                     "INSERT INTO orders (customer_name, customer_email, total_amount) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            psOrder.setString(1, order.getCustomerName());
            psOrder.setString(2, order.getCustomerEmail());
            psOrder.setDouble(3, order.getTotalAmount());

            int affectedRows = psOrder.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = psOrder.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

            if (orderId > 0) {
                String sqlDetail = "INSERT INTO order_details (order_id, menu_item_id, quantity, special_instructions) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psDetail = connection.prepareStatement(sqlDetail)) {
                    for (OrderDetail detail : order.getOrderDetails()) {
                        psDetail.setInt(1, orderId);
                        psDetail.setInt(2, detail.getMenuItem().getId());
                        psDetail.setInt(3, detail.getQuantity());
                        psDetail.setString(4, detail.getSpecialInstructions());


                        psDetail.addBatch();
                    }

                    psDetail.executeBatch();
                }
            }
        } catch (SQLException e) {
            // Log the SQL exception for debugging
            e.printStackTrace();
        }
        return orderId;
    }

    public static List<Order> getAllOrdersWithDetails() {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.id AS order_id, o.customer_name, o.customer_email, o.order_date, o.total_amount, " +
                     "od.quantity, od.special_instructions, " +
                     "mi.name AS menu_item_name, mi.category, mi.description, mi.price " +
                     "FROM orders o " +
                     "JOIN order_details od ON o.id = od.order_id " +
                     "JOIN menu_items mi ON od.menu_item_id = mi.id " +
                     "ORDER BY o.id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Order currentOrder = null;
            int currentOrderId = 0;

            while (rs.next()) {
                int orderId = rs.getInt("order_id");

                if (orderId != currentOrderId) {
                    currentOrderId = orderId;
                    currentOrder = new Order();
                    currentOrder.setId(orderId);
                    currentOrder.setCustomerName(rs.getString("customer_name"));
                    currentOrder.setCustomerEmail(rs.getString("customer_email"));
                    currentOrder.setTotalAmount(rs.getDouble("total_amount"));
                    currentOrder.setOrderDate(rs.getTimestamp("order_date"));
                    currentOrder.setOrderDetails(new ArrayList<>());

                    orders.add(currentOrder);
                }

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setSpecialInstructions(rs.getString("special_instructions"));

                MenuItem menuItem = new MenuItem();
                menuItem.setName(rs.getString("menu_item_name"));
                menuItem.setCategory(rs.getString("category"));
                menuItem.setDescription(rs.getString("description"));
                menuItem.setPrice(rs.getDouble("price"));

                orderDetail.setMenuItem(menuItem);
                currentOrder.getOrderDetails().add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
