<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/goods.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <c:if test="${param.created == 'true'}">
            <div class="container">
                <h1>Adding goods</h1>
                <p>Adding goods successfully completed</p>
                <button onclick="location.href='home-page'" class="registerbtn">Back to marketplace</button>
            </div>
        </c:if>
            <form action="/goods" method="POST" enctype="multipart/form-data">
                <div class="container">
                    <h1>Adding goods</h1>
                    <p>Please fill in this form to add new goods.</p>
                    <hr>
                    <h6>

                    <label for="name"><b>Name</b></label>
                    <input type="text" placeholder="Enter Name" name="name" required>

                    <label for="model"><b>Model</b></label>
                    <input type="text" placeholder="Enter Model" name="model" required>

                    <label for="price"><b>Price</b></label>
                    <input type="text" pattern="^\d+(?:[\.,]\d{1,2})?$" placeholder="Enter Price" name="price" required>
                        <c:if test="${errorsMap.containsKey('price')}">
                            <p class="validation-error">${errorsMap.get("price")}</p>
                        </c:if>

                    <label for="category"><b>Category</b></label>
                    <select class="select-css" id="category" name="category">
                        <c:forEach var="item" items="${categoryList}">
                            <option value="${item.getCategoryId()}">${item.getCategoryName()}</option>
                        </c:forEach>
                        <c:if test="${errorsMap.containsKey('category')}">
                            <p class="validation-error">${errorsMap.get("category")}</p>
                        </c:if>
                    </select>
                    <br>

                    <label for="description"><b>Description</b></label>
                    <input type="text" placeholder="Enter Description" name="description" required>

                    <label for="manufacturer"><b>Manufacturer</b></label>
                    <select class="select-css" id="manufacturer" name="manufacturer">
                        <c:forEach var="item" items="${manufacturersList}">
                            <option value="${item.getManufacturerId()}">${item.getManufacturerName()}</option>
                        </c:forEach>
                        <c:if test="${errorsMap.containsKey('manufacturer')}">
                            <p class="validation-error">${errorsMap.get("manufacturer")}</p>
                        </c:if>
                    </select>
                    <br>

                    <label for="image"><b>Image:</b></label>
                    <input type="file" name="file" id="file">
                        <c:if test="${errorsMap.containsKey('image')}">
                            <p class="validation-error">${errorsMap.get("image")}</p>
                        </c:if>

                    </h6>
                    <button type="submit" class="registerbtn">Create</button>
                </div>
            </form>
    </body>
</html>
