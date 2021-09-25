<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/login.css" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <form action="/action/login" method="post">
            <div class="container">


                <h1><fmt:message key="msg.LOGIN" bundle="${menu}"/></h1>
                <p><fmt:message key="msg.login-comment" bundle="${message}"/></p>
                <hr>

                <c:if test="${errorsMap.containsKey('authorization')}">
                    <p class="validation-error"><fmt:message key="msg.error-not-authorized" bundle="${message}"/></p>
                </c:if>
                <c:if test="${errorsMap.containsKey('activation')}">
                    <p class="validation-error"><fmt:message key="msg.error-not-activate" bundle="${message}"/></p>
                </c:if>
                <c:if test="${param.registration eq 'true'}">
                    <p class="green-text"><fmt:message key="msg.authorized-true-message" bundle="${message}"/></p>
                </c:if>

                <label for="email"><b><fmt:message key="msg.email" bundle="${menu}"/></b></label>
                <input type="email" pattern=".+@.+\..+"  placeholder=<fmt:message key='msg.email-place-holder' bundle="${menu}"/> name="email" required>
                <c:if test="${errorsMap.containsKey('email')}">
                    <p class="validation-error"><fmt:message key="${errorsMap.get('email')}" bundle='${message}'/></p>
                </c:if>

                <label for="psw"><b><fmt:message key="msg.password" bundle="${menu}"/></b></label>
                <input type="password" pattern="(?=.*[0-9])(?=.*[a-zа-я])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}" placeholder=<fmt:message key='msg.password-place-holder' bundle='${menu}'/> name="psw" required>
                <c:if test="${errorsMap.containsKey('psw')}">
                    <p class="validation-error"><fmt:message key="${errorsMap.get('psw')}" bundle='${message}'/></p>
                </c:if>


                <button type="submit" class="loginbtn"><fmt:message key="msg.LOGIN" bundle="${menu}"/></button>

            </div>
        </form>

    </body>
</html>



