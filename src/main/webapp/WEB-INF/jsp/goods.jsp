<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/goods.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <c:if test="${param.created == 'true'}">
            <div class="container">
                <h1><fmt:message key="msg.adding-goods" bundle="${menu}"/></h1>
                <p><fmt:message key="msg.adding-goods-true" bundle="${message}"/></p>
                <button onclick="location.href='home-page'" class="registerbtn"><fmt:message key="msg.back-to-marketplace" bundle="${menu}"/></button>
            </div>
        </c:if>
            <form action="/action/goods" method="POST" enctype="multipart/form-data">
                <div class="container">
                    <h1><fmt:message key="msg.adding-goods" bundle="${menu}"/></h1>
                    <p><fmt:message key="msg.goods-fill-label" bundle="${message}"/></p>
                    <hr>
                    <h6>

                    <label for="name"><b><fmt:message key="msg.name" bundle="${menu}"/></b></label>
                    <input type="text" placeholder=<fmt:message key="msg.name-place-holder" bundle="${menu}"/> name="name" required>

                    <label for="model"><b><fmt:message key="msg.model" bundle="${menu}"/></b></label>
                    <input type="text" placeholder=<fmt:message key="msg.model-place-holder" bundle="${menu}"/> name="model" required>

                    <label for="price"><b><fmt:message key="msg.price" bundle="${menu}"/></b></label>
                    <input type="text" placeholder=<fmt:message key="msg.price-place-holder" bundle="${menu}"/> name="price" required>
                        <c:if test="${errorsMap.containsKey('price')}">
                            <p class="validation-error"><fmt:message key="${errorsMap.get('price')}" bundle="${message}"/></p>
                        </c:if>

                    <label for="category"><b><fmt:message key="msg.category" bundle="${menu}"/></b></label>
                    <select class="select-css" id="category" name="category">
                        <c:forEach var="item" items="${categoryList}">
                            <option value="${item.getCategoryId()}">${item.getCategoryName()}</option>
                        </c:forEach>
                        <c:if test="${errorsMap.containsKey('category')}">
                            <p class="validation-error"><fmt:message key="${errorsMap.get('category')}" bundle="${message}"/></p>
                        </c:if>
                    </select>
                    <br>

                    <label for="description"><b><fmt:message key="msg.description" bundle="${menu}"/></b></label>
                    <input type="text" placeholder=<fmt:message key="msg.description-place-holder" bundle="${menu}"/> name="description" required>

                    <label for="manufacturer"><b><fmt:message key="msg.manufacturer" bundle="${menu}"/></b></label>
                    <select class="select-css" id="manufacturer" name="manufacturer">
                        <c:forEach var="item" items="${manufacturersList}">
                            <option value="${item.getManufacturerId()}">${item.getManufacturerName()}</option>
                        </c:forEach>
                        <c:if test="${errorsMap.containsKey('manufacturer')}">
                            <p class="validation-error"><fmt:message key="${errorsMap.get('manufacturer')}" bundle="${message}"/></p>
                        </c:if>
                    </select>
                    <br>

                    <label for="image"><b><fmt:message key="msg.image" bundle="${menu}"/></b></label>
                    <input type="file" name="file" id="file">
                        <c:if test="${errorsMap.containsKey('image')}">
                            <p class="validation-error"><fmt:message key="${errorsMap.get('image')}" bundle="${message}"/></p>
                        </c:if>

                    </h6>
                    <button type="submit" class="registerbtn"><fmt:message key="msg.create" bundle="${menu}"/></button>
                </div>
            </form>
    </body>
</html>
