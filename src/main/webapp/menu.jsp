<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Menu</title>
    <%@include file="cdn.jsp" %>
    <link rel="stylesheet" type="text/css" href="styles/styles.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="#">Restaurant</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="#menu">Menu</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#cart">Cart</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-5 pt-5">
        <h1 id="menu" class="my-5 text-center">Restaurant Menu</h1>
        
        <!-- Iterate over categories -->
        <c:forEach var="entry" items="${menuMap}">
            <h2 class="mt-4">${entry.key}</h2>
            <div class="row">
                <c:forEach var="item" items="${entry.value}">
                    <div class="col-md-4">
                        <div class="card mb-4">
                            <div class="card-body">
                                <h5 class="card-title">${item.name}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">${item.category}</h6>
                                <p class="card-text">${item.description}</p>
                                <p class="card-text">Price: ${item.price}</p>
                                <form action="cart" method="post">
                                    <input type="hidden" name="menuItemId" value="${item.id}">
                                    <div class="form-group">
                                        <label for="quantity-${item.id}">Quantity:</label>
                                        <input type="number" class="form-control" id="quantity-${item.id}" name="quantity" value="1" min="1">
                                    </div>
                                    <div class="form-group">
                                        <label for="specialInstructions-${item.id}">Special Instructions:</label>
                                        <textarea class="form-control" id="specialInstructions-${item.id}" name="specialInstructions" placeholder="Special instructions"></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Add to Cart</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
    
    <div class="container my-5" id="cart">
        <c:import url="cart.jsp"></c:import>
    </div>

   
</body>
</html>
