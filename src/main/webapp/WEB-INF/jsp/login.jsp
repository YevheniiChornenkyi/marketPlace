<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="test" />

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/login.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <form action="/action/login" method="post">
            <div class="container">

                <div><fmt:message key="msg.test"/></div>

                <h1>Login</h1>
                <p>Please enter your credentials to login.</p>
                <hr>

                <c:if test="${errorsMap.containsKey('authorization')}">
                    <p class="validation-error">You must be authorized for this action.</p>
                </c:if>
                <c:if test="${errorsMap.containsKey('activation')}">
                    <p class="validation-error">This account does not activate</p>
                </c:if>
                <c:if test="${param.registration eq 'true'}">
                    <p class="green-text">We send activation code to specified email. Please follow the specified link to activate you account.</p>
                </c:if>

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



