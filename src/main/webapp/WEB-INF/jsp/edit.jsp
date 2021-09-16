<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/goods.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <c:choose>
            <c:when test="${empty param.created}">
                <form action="/edit" method="POST" enctype="multipart/form-data">
                    <div class="container">

                        <h1>Editing</h1>
                        <p>Please edit in this form and click on &#34;Save&#34;.</p>
                        <hr>
                        <h6>

                        <input type="hidden" value="${goodsToEdit.getId()}" name="id" required>
                        <input type="hidden" value="${goodsToEdit.getImageName()}" name="oldImageName" required>

                        <label for="name"><b>Name</b></label>
                        <input type="text" value="${goodsToEdit.getName()}" name="name" required>

                        <label for="model"><b>Model</b></label>
                        <input type="text" value="${goodsToEdit.getModel()}" name="model" required>

                        <label for="price"><b>Price</b></label>
                        <input type="text" pattern="^\d+(?:[\.,]\d{1,2})?$" value="${goodsToEdit.getPrice()}" name="price" required>
                            <c:if test="${errorsMap.containsKey('price')}">
                                <p class="validation-error">${errorsMap.get("price")}</p>
                            </c:if>

                        <label for="category"><b>Category</b></label>
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
                            <p class="validation-error">${errorsMap.get("category")}</p>
                        </c:if>
                        <br>

                        <label for="description"><b>Description</b></label>
                        <input type="text" value="${goodsToEdit.getDescription()}" name="description" required>

                        <label for="manufacturer"><b>Manufacturer</b></label>
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
                                    <p class="validation-error">${errorsMap.get("manufacturer")}</p>
                                </c:if>
                            </select>
                        <br>

                        <label for="image"><b>Image:</b></label>
                        <input type="file" value="D:\marketPlaceImages.axe.png" name="file" id="file">
                            <c:if test="${errorsMap.containsKey('image')}">
                                <p class="validation-error">${errorsMap.get("image")}</p>
                            </c:if>

                        </h6>
                        <button type="submit" class="registerbtn">Save</button>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${param.created == 'true'}">
                        <div class="container">
                            <h1>Editing</h1>
                            <p>Editing successfully completed</p>
                            <button onclick="location.href='home-page'" class="registerbtn">Back to marketplace</button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="container">
                            <h1>Editing</h1>
                            <h3><p>Editing failed</p></h3>
                            <p>An error occurred during editing, check the logs.</p>
                            <button onclick="location.href='home-page'" class="registerbtn">Back to marketplace</button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </body>
</html>
