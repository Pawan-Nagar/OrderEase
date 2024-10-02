<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*, com.restaurant.model.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="container">
    <h1 class="my-4">Your Cart</h1>
    <c:if test="${cart != null && !cart.isEmpty()}">
        <form action="checkout" method="post">
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
                    <c:forEach var="item" items="${cart.values()}">
                        <tr>
                            <td>${item.menuItem.name}</td>
                            <td>${item.quantity}</td>
                            <td>${item.specialInstructions}</td>
                            <td>${item.menuItem.price}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3">Total</td>
                        <td>
                            <c:set var="totalAmount" value="0" />
                            <c:forEach var="item" items="${cart.values()}">
                                <c:set var="totalAmount" value="${totalAmount + (item.menuItem.price * item.quantity)}" />
                            </c:forEach>
                            <c:out value="${totalAmount}" />
                            <input type="hidden" name="totalAmount" value="${totalAmount}" />
                        </td>
                    </tr>
                </tbody>
            </table>
            
            <button type="submit" class="btn btn-primary">Checkout</button>
        </form>
    </c:if>
    <c:if test="${cart == null || cart.isEmpty()}">
        <p>Your cart is empty. <a href="menu">Refresh</a>.</p>
    </c:if>
</body>
</html>
