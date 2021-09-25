<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/registration.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <form action="/action/registration" method="POST">
          <div class="container">
            <h1><fmt:message key="msg.registration" bundle="${menu}"/></h1>
            <p><fmt:message key="msg.registration-label" bundle="${message}"/></p>
            <hr>

            <label for="name"><b><fmt:message key="msg.name" bundle="${menu}"/></b></label>
            <input type="text" placeholder=<fmt:message key="msg.name-place-holder" bundle="${menu}"/> name="name" required>

            <label for="surname"><b><fmt:message key="msg.surname" bundle="${menu}"/></b></label>
            <input type="text" placeholder=<fmt:message key="msg.surname-place-holder" bundle="${menu}"/> name="surname" required>

            <label for="email"><b><fmt:message key="msg.email" bundle="${menu}"/></b></label>
            <input type="text" pattern=".+@.+\..+" placeholder=<fmt:message key="msg.email-place-holder" bundle="${menu}"/> name="email" required>
            <c:if test="${errorsMap.containsKey('email')}">
                <p class="validation-error"><fmt:message key="${errorsMap.get('email')}" bundle="${message}"/></p>
            </c:if>

            <label for="psw"><b><fmt:message key="msg.password" bundle="${menu}"/></b></label>
            <input type="password" pattern="(?=.*[0-9])(?=.*[a-zа-я])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}" placeholder=<fmt:message key="msg.password-place-holder" bundle="${menu}"/> name="psw" required>
            <c:if test="${errorsMap.containsKey('psw')}">
                 <p class="validation-error"><fmt:message key="${errorsMap.get('psw')}" bundle="${message}"/></p>
            </c:if>

            <label for="psw-repeat"><b><fmt:message key="msg.repeat-password" bundle="${menu}"/></b></label>
            <input type="password" placeholder=<fmt:message key="msg.repeat-password" bundle="${menu}"/> name="psw-repeat" required>
            <c:if test="${errorsMap.containsKey('pswRepeat')}">
                 <p class="validation-error"><fmt:message key="msg.psw-mismatch" bundle="${message}"/></p>
            </c:if>

            <button type="submit" class="registerbtn"><fmt:message key="msg.register" bundle="${message}"/></button>
          </div>
        </form>
    </body>
</html>



