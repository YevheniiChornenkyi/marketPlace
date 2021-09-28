<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/homepage.css" rel="stylesheet">
        <script src="/static/js/slideBar.js"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-sm navbar-light bg-white border-bottom">
        <a class="navbar-brand ml-2 font-weight-bold" href="/"><span id="orange">Kharkiv tools</span></a>
                <div class="make-center">
                    <c:choose>
                        <c:when test="${empty authentication}">
                            <div>
                                <div><h6><fmt:message key="msg.user-greetings" bundle="${message}"/></h6></div>
                                <button onclick="location.href='login'" class="log-reg-button"><fmt:message key="msg.LOGIN" bundle="${menu}"/></button>
                                <button onclick="location.href='registration'" class="log-reg-button"><fmt:message key="msg.REGISTER" bundle="${menu}"/></button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div>
                                <div><h6><fmt:message key="msg.welcome" bundle="${message}"/> ${authentication.getName()} ${authentication.getSurName()}.</h6></div>
                                <div style="display:inline-block;white-space:nowrap;">
                                <c:if test="${authentication.getRole().getId() eq '2'}">
                                    <button onclick="location.href='logout'" class="cart-acc-button"><fmt:message key="msg.LOGOUT" bundle="${menu}"/></button>
                                    <button onclick="location.href='orders'" class="cart-acc-button "><fmt:message key="msg.my-orders" bundle="${menu}"/></button>
                                </c:if>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
                <a href="/cart" >
                    <img src="/static/icons/cart.png" alt="add to cart" class="make-right">
                </a>
                <button onclick="location.href='home-page?lang=ru'" class="button-lang"><fmt:message key="msg.RU" bundle="${menu}"/></button>
                <button onclick="location.href='home-page?lang=en'" class="button-lang"><fmt:message key="msg.EN" bundle="${menu}"/></button>
        </nav>

<!-- Sidebar filter section -->
        <section id="sidebar">

            <c:if test="${authentication.getRole() eq 'ADMIN'}">
                <div class="border-bottom pb-2 ml-2">
                    <h5><fmt:message key="msg.admin-menu" bundle="${menu}"/></h5>
                    <button onclick="location.href='goods'" class="cart-acc-button "><fmt:message key="msg.add-new-product" bundle="${menu}"/></button>
                    <button onclick="location.href='users'" class="cart-acc-button "><fmt:message key="msg.users" bundle="${menu}"/></button>
                    <button onclick="location.href='orders'" class="cart-acc-button "><fmt:message key="msg.orders" bundle="${menu}"/></button>
                    <button onclick="location.href='logout'" class="cart-acc-button"><fmt:message key="msg.LOGOUT" bundle="${menu}"/></button>
                </div>
            </c:if>

            <div class="border-bottom pb-2 ml-2">
                <h4><fmt:message key="msg.filters" bundle="${menu}"/></h4>
            </div>

            <div class="py-2 border-bottom ml-3">
                <form action="/home-page" method="GET">
                    <h5><fmt:message key="msg.price" bundle="${menu}"/></h5>

                    <section class="range-slider" name="slideBar">
                      <span class="rangeValues"></span>
                      <input value="0" min="0" max="10000" step="50" type="range" name="minPrice">
                      <input value="100500" min="0" max="10000" step="50" type="range" name="maxPrice">
                    </section>

                    <h5><fmt:message key="msg.categories" bundle="${menu}"/></h5>
                    <c:forEach var="item" items="${categoriesList}">
                        <input type="checkbox" name="categories" value="${item.getCategoryId()}">${item.getCategoryName()}<BR>
                    </c:forEach>
            </div>
                <h4><fmt:message key="msg.sort-by" bundle="${menu}"/></h4>
                    <div id="sorting" class="border rounded p-1 m-1"> <select name="sort" id="sort">
                            <option value="ALPHABETIC"><b><fmt:message key="msg.alphabetic" bundle="${menu}"/></b></option>
                            <option value="PRICE"><b><fmt:message key="msg.price" bundle="${menu}"/></b></option>
                            <option value="DATE"><b><fmt:message key="msg.date" bundle="${menu}"/></b></option>
                        </select> </div>
                        <h5><fmt:message key="msg.order" bundle="${menu}"/></h5>
                            <div id="sorting" class="border rounded p-1 m-1"> <select name="order" id="order">
                                    <option value="ASCENDING"><b><fmt:message key="msg.ascending" bundle="${menu}"/></b></option>
                                    <option value="DESCENDING"><b><fmt:message key="msg.descending" bundle="${menu}"/></b></option>
                                </select> </div>
                        </div>
                </div>
                <button class="cart-acc-button"><fmt:message key="msg.apply" bundle="${menu}"/></button>
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
                                    <h6><b><fmt:message key="msg.name" bundle="${menu}"/>: ${item.getName()}</b> </h6>
                                    <h6><b><fmt:message key="msg.category" bundle="${menu}"/>: ${item.getCategoryName()}</b> </h6>
                                    <h6><b><fmt:message key="msg.manufacturer" bundle="${menu}"/>: ${item.getManufacturerName()}</b> </h6>
                                    <h6><b><fmt:message key="msg.added" bundle="${menu}"/>: ${item.getCreated()}</b> </h6>
                                    <div class="d-flex flex-row">
                                        <div class="text-muted"><fmt:message key="msg.price" bundle="${menu}"/>: ${item.getPrice()} &#8372</div>
                                    </div>
                                    <c:choose>
                                        <c:when test="${authentication.getRole() eq 'ADMIN'}">
                                            <form action="/edit" method="GET" id="add-to-cart">
                                                <div>
                                                <input type="hidden" name="goodsId" value="${item.getId()}">
                                                <button class="cart-add"><fmt:message key="msg.edit" bundle="${menu}"/></button>
                                                </div>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="/action/home-page" method="POST" id="add-to-cart">
                                                <div class="add">
                                                <input type="hidden" name="id" value="${item.getId()}" required>
                                                <button class="cart-add"><fmt:message key="msg.add-to-cart" bundle="${menu}"/></button>
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