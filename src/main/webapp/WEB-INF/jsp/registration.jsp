<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/registration.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="UTF-8" />
    </head>
    <body>
        <form action="/registration" method="POST">
          <div class="container">
            <h1>Registration</h1>
            <p>Please fill in this form to create an account.</p>
            <hr>

            <label for="name"><b>Name</b></label>
            <input type="text" placeholder="Enter Name" name="name" required>

            <label for="surname"><b>Surname</b></label>
            <input type="text" placeholder="Enter Surname" name="surname" required>

            <label for="email"><b>Email</b></label>
            <input type="text" pattern=".+@.+\..+" placeholder="Enter Email" name="email" required>
            <c:if test="${errorsMap.containsKey('email')}">
                <p class="validation-error">${errorsMap.get("email")}</p>
            </c:if>
            <c:if test="${errorsMap.containsKey('emailAlreadyExist')}">
                <p class="validation-error">${errorsMap.get("emailAlreadyExist")}</p>
            </c:if>

            <label for="psw"><b>Password</b></label>
            <input type="password" pattern="(?=.*[0-9])(?=.*[a-zа-я])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}" placeholder="Enter Password" name="psw" required>
            <c:if test="${errorsMap.containsKey('psw')}">
                 <p class="validation-error">${errorsMap.get("psw")}</p>
            </c:if>

            <label for="psw-repeat"><b>Repeat Password</b></label>
            <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
            <c:if test="${errorsMap.containsKey('pswRepeat')}">
                 <p class="validation-error">${errorsMap.get("pswRepeat")}</p>
            </c:if>

            <button type="submit" class="registerbtn">Register</button>
          </div>
        </form>
    </body>
</html>



