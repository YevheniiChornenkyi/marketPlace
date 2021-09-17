<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/homepage.css" rel="stylesheet">
    </head>

    <body>
        <nav class="navbar navbar-expand-sm navbar-light bg-white border-bottom">
        <a class="navbar-brand ml-2 font-weight-bold" href="/"><span id="orange">Kharkiv tools</span></a>
                <div class="make-center">
                    <c:choose>
                        <c:when test="${empty authentication}">
                            <div>
                                <div><h6>Welcome visitor.
                                Register, or log in if you already have an account</h6></div>
                                <button onclick="location.href='login'" class="log-reg-button">LOGIN</button>
                                <button onclick="location.href='registration'" class="log-reg-button">REGISTER</button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <div><h6>Welcome ${authentication.getName()} ${authentication.getSurName()}.</h6></div>
                                <div style="display:inline-block;white-space:nowrap;">
                                <button onclick="location.href='logout'" class="cart-acc-button">LOGOUT</button>
                                <button onclick="location.href='registration'" class="cart-acc-button ">My account</button>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
                <a href="/cart" >
                    <img src="/static/icons/cart.png" alt="add to cart" class="make-right">
                </a>
        </nav>

<!-- Sidebar filter section -->
        <section id="sidebar">

            <c:if test="${authentication.getRole() eq 'ADMIN'}">
                <div class="border-bottom pb-2 ml-2">
                    <h5>Admin menu</h5>
                    <button onclick="location.href='goods'" class="cart-acc-button ">Add new product</button>
                    <button onclick="location.href='goods'" class="cart-acc-button ">Users</button>
                    <button onclick="location.href='goods'" class="cart-acc-button ">Orders</button>
                </div>
            </c:if>

            <div class="border-bottom pb-2 ml-2">
                <h4>Filters</h4>
            </div>
            <div class="py-2 border-bottom ml-3">
                <h5>Categories</h5>
                <form action="/home-page" method="GET">

                    <c:forEach var="item" items="${categoriesList}">
                        <input type="checkbox" name="categories" value="${item.getCategoryId()}">${item.getCategoryName()}<BR>
                    </c:forEach>
            </div>
                <h4>Sort by</h4>
                    <div id="sorting" class="border rounded p-1 m-1"> <select name="sort" id="sort">
                            <option value="ALPHABETIC"><b>Alphabetic</b></option>
                            <option value="PRICE"><b>Price</b></option>
                            <option value="DATE"><b>Date</b></option>
                        </select> </div>
                        <h5>Order</h5>
                            <div id="sorting" class="border rounded p-1 m-1"> <select name="order" id="order">
                                    <option value="ASCENDING"><b>Ascending</b></option>
                                    <option value="DESCENDING"><b>Descending</b></option>
                                </select> </div>
                        </div>
                </div>
                <button class="cart-acc-button">Apply</button>
            </form>
        </section>

<!-- products section -->
        <section id="products">
            <div class="container">
                <div class="d-flex flex-row">

                </div>

                <div class="row">
                    <c:forEach var="item" items="${goodsViewDTOList}">
                        <p>${user}</p>
                        <div class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1">
                        <div class="card">
                             <img class="card-img-top" src="/image?id=${item.getImageName()}">
                                    <h6><b>Name: ${item.getName()}</b> </h6>
                                    <h6><b>Category: ${item.getCategoryName()}</b> </h6>
                                    <h6><b>Manufacturer: ${item.getManufacturerName()}</b> </h6>
                                    <h6><b>Added: ${item.getCreated()}</b> </h6>
                                    <div class="d-flex flex-row">
                                        <div class="text-muted">Price ${item.getPrice()} &#8372</div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${authentication.getRole() eq 'ADMIN'}">
                                            <form action="/edit" method="GET" id="add-to-cart">
                                                <div>
                                                <input type="hidden" name="goodsId" value="${item.getId()}">
                                                <button class="cart-acc-button">Edit</button>
                                                </div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="/home-page" method="POST" id="add-to-cart">
                                                <input type="hidden" name="id" value="${item.getId()}" required>
                                                <div class="add">
                                                <input class="add" type="image" name="submit"
                                                    src="/static/icons/Add-To-Cart.jpg"
                                                    alt="add to cart">
                                                </div>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
    </body>
</html>