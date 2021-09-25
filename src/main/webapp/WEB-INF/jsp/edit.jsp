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
        <c:choose>
            <c:when test="${empty param.created}">
                <form action="/action/edit" method="POST" enctype="multipart/form-data">
                    <div class="container">

                        <h1><fmt:message key="msg.editing" bundle="${menu}"/></h1>
                        <p><fmt:message key="msg.orders.label" bundle="${message}"/></p>
                        <hr>
                        <h6>

                        <input type="hidden" value="${goodsToEdit.getId()}" name="id" required>
                        <input type="hidden" value="${goodsToEdit.getImageName()}" name="oldImageName" required>

                        <label for="name"><b><fmt:message key="msg.name" bundle="${menu}"/></b></label>
                        <input type="text" value="${goodsToEdit.getName()}" name="name" required>

                        <label for="model"><b><fmt:message key="msg.model" bundle="${menu}"/></b></label>
                        <input type="text" value="${goodsToEdit.getModel()}" name="model" required>

                        <label for="price"><b><fmt:message key="msg.price" bundle="${menu}"/></b></label>
                        <input type="text" pattern="^\d+(?:[\.,]\d{1,2})?$" value="${goodsToEdit.getPrice()}" name="price" required>
                            <c:if test="${errorsMap.containsKey('price')}">
                                <p class="validation-error"><fmt:message key="${errorsMap.get('price')}" bundle="${message}"/></p>
                            </c:if>

                        <label for="category"><b><fmt:message key="msg.category" bundle="${menu}"/></b></label>
                        <select selected="${goodsToEdit.getCategoryName()}" class="select-css" id="category" name="category">
                            <c:forEach var="item" items="${categoryList}">
                                <c:choose>
                                    <c:when test="${{item.getCategoryName()} eq {goodsToEdit.getCategoryName()}}">
                                        <option value="${item.getCategoryId()}" selected="${item.getCategoryName()}">${item.getCategoryName()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${item.getCategoryId()}">${item.getCategoryName()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <c:if test="${errorsMap.containsKey('category')}">
                            <p class="validation-error"><fmt:message key="${errorsMap.get('category')}" bundle="${message}"/></p>
                        </c:if>
                        <br>

                        <label for="description"><b><fmt:message key="msg.description" bundle="${menu}"/></b></label>
                        <input type="text" value="${goodsToEdit.getDescription()}" name="description" required>

                        <label for="manufacturer"><b><fmt:message key="msg.manufacturer" bundle="${menu}"/></b></label>
                            <select selected="${goodsToEdit.getManufacturerName()}" class="select-css" id="manufacturer" name="manufacturer">
                                <c:forEach var="item" items="${manufacturersList}">
                                    <c:choose>
                                        <c:when test="${{item.getManufacturerName()} eq {goodsToEdit.getManufacturerName()}}">
                                            <option value="${item.getManufacturerId()}" selected="${item.getManufacturerName()}">${item.getManufacturerName()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${item.getManufacturerId()}">${item.getManufacturerName()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${errorsMap.containsKey('manufacturer')}">
                                    <p class="validation-error"><fmt:message key="${errorsMap.get('manufacturer')}" bundle="${message}"/></p>
                                </c:if>
                            </select>
                        <br>

                        <label for="image"><b><fmt:message key="msg.image" bundle="${menu}"/></b></label>
                        <input type="file" value="D:\marketPlaceImages.axe.png" name="file" id="file">
                            <c:if test="${errorsMap.containsKey('image')}">
                                <p class="validation-error"><fmt:message key="${errorsMap.get('image')}" bundle="${message}"/></p>
                            </c:if>

                        </h6>
                        <button type="submit" class="registerbtn"><fmt:message key="msg.save" bundle="${menu}"/></button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${param.created == 'true'}">
                        <div class="container">
                            <h1><fmt:message key="msg.editing" bundle="${menu}"/></h1>
                            <p><fmt:message key="msg.editing-done" bundle="${message}"/></p>
                            <button onclick="location.href='home-page'" class="registerbtn"><fmt:message key="msg.editing-done" bundle="${message}"/></button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="container">
                            <h1><fmt:message key="msg.editing" bundle="${menu}"/></h1>
                            <h3><p><fmt:message key="msg.editing-fail" bundle="${message}"/></p></h3>
                            <p><fmt:message key="msg.editing.error" bundle="${message}"/></p>
                            <button onclick="location.href='home-page'" class="registerbtn"><fmt:message key="msg.back-to-marketplace" bundle="${menu}"/></button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </body>
</html>
