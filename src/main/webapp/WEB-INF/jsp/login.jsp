<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="/static/css/login.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

<form action="/login" method="post">
  <div class="container">
    <h1>Login</h1>
    <p>Please enter your credentials to login.</p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input type="email" pattern=".+@.+\..+"  placeholder="Enter Email" name="email" required>
    <c:if test="${errorsMap.containsKey('email')}">
        <p class="validation-error">${errorsMap.get("email")}</p>
    </c:if>

    <label for="psw"><b>Password</b></label>
    <input type="password" pattern="(?=.*[0-9])(?=.*[a-zа-я])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}" placeholder="Enter Password" name="psw" required>
    <c:if test="${errorsMap.containsKey('psw')}">
        <p class="validation-error">${errorsMap.get("psw")}</p>
    </c:if>


    <button type="submit" class="loginbtn">Login</button>
  </div>

</form>

</body>
</html>



