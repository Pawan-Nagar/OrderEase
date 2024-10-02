<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Admin Panel</title>
    <%@ include file="cdn.jsp" %>
    <style>
        body {
            scroll-behavior: smooth;
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        nav.navbar {
            background-color: #343a40;
        }
        .navbar-brand, .nav-link {
            color: white !important;
        }
        .nav-link:hover {
            color: #d4d4d4 !important;
        }
        .container {
            margin-top: 30px;
        }
        h2 {
            border-bottom: 2px solid #343a40;
            padding-bottom: 10px;
            margin-bottom: 20px;
            color: #343a40;
        }
        .form-group label {
            font-weight: bold;
            color: #343a40;
        }
        .form-control {
            border-radius: 5px;
            border: 1px solid #ced4da;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #bd2130;
            border-color: #b21f2d;
        }
        table {
            background-color: white;
            border: 1px solid #dee2e6;
        }
        table thead th {
            background-color: #343a40;
            color: white;
        }
        table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        table tbody tr:hover {
            background-color: #e9ecef;
        }
        .section {
            margin-bottom: 100px;
        }
        .navbar-toggler {
            border-color: rgba(255, 255, 255, 0.1);
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="admin">Rare-Restaurant</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="#addMenuItem">Add</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#updateDeleteMenuItem">Update/Delete</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#orders">Orders</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <section id="addMenuItem" class="section">
        <h2>Add Menu Item</h2>
        <form action="admin" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="category">Category:</label>
                <input type="text" id="category" name="category" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" class="form-control" required></textarea>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="text" id="price" name="price" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Item</button>
        </form>
    </section>

    <section id="updateDeleteMenuItem" class="section">
        <h2>Update or Delete Menu Item</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Category</th>
                <th>Description</th>
                <th>Price</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${menuItems}">
                <tr>
                    <td>
                        <form action="admin#updateDeleteMenuItem" method="post" style="display: inline;">
                            <input type="text" name="id" value="${item.id}" readonly>
                    </td>
                    <td><input type="text" name="name" value="${item.name}"></td>
                    <td><input type="text" name="category" value="${item.category}"></td>
                    <td><input type="text" name="description" value="${item.description}"></td>
                    <td><input type="text" name="price" value="${item.price}"></td>
                    <td>
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="btn btn-primary btn-sm">Update</button>
                        </form>
                    </td>
                    <td>
                        <form action="admin#updateDeleteMenuItem" method="post" style="display: inline;">
                            <input type="hidden" name="id" value="${item.id}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>

    <section id="orders" class="section">
        <h2>Order Details</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Customer Email</th>
                <th>Order Date</th>
                <th>Total Amount</th>
                <th>Menu Items</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.customerName}</td>
                    <td>${order.customerEmail}</td>
                    <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${order.totalAmount}</td>
                    <td>
                        <ul>
                            <c:forEach var="detail" items="${order.orderDetails}">
                                <li>${detail.menuItem.name} (Category: ${detail.menuItem.category}, Price: ${detail.menuItem.price}, Quantity: ${detail.quantity}, Instructions: ${detail.specialInstructions})</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:set var="totalAllOrders" value="0" />
        <c:forEach var="order" items="${orders}">
            <c:set var="totalAllOrders" value="${totalAllOrders + order.totalAmount}" />
        </c:forEach>
        <h3>Total Amount of All Orders: ${totalAllOrders}</h3>
    </section>
</div>
</body>
</html>
