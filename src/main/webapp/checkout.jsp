<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*, com.restaurant.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <%@include file="cdn.jsp" %>
    <link rel="stylesheet" type="text/css" href="styles/styles.css">
</head>
<body>
    <div class="container">
        <div class="jumbotron mt-5">
            <h1 class="display-4">Order Confirmation</h1>
            <p class="lead">Thank you for your order, ${order.customerName}!</p>
            <hr class="my-4">
            <p>Order ID: ${orderId}</p>
            <p>Total Amount: ${order.totalAmount}</p>
        </div>
        
        <h2 class="mb-4">Order Details</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Instructions</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="detail" items="${order.orderDetails}">
                    <tr>
                        <td>${detail.menuItem.name}</td>
                        <td>${detail.quantity}</td>
                        <td>${detail.specialInstructions}</td>
                        <td>${detail.menuItem.price}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p><a href="menu" class="btn btn-primary">Back to Menu</a></p>
    </div>

</body>
</html>
