<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/goods.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
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
            <input type="text" placeholder="Enter Price" name="price" required>

            <label for="category"><b>Category</b></label>
            <input type="text" placeholder="Enter Category" name="category" required>

            <label for="description"><b>Description</b></label>
            <input type="text" placeholder="Enter Description" name="description" required>

            <label for="manufacturer"><b>Manufacturer</b></label>
            <input type="text" placeholder="Enter Manufacturer" name="manufacturer" required>

            <label for="model"><b>Surname</b></label>
            <input type="text" placeholder="Enter Model" name="model" required>

            <label for="image"><b>Image:</b></label>
            <input type="file" name="file" id="file">

            </h6>
            <button type="submit" class="registerbtn">Create</button>
          </div>
        </form>
    </body>
</html>
