<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/activation.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
         <div class="text">
             <c:if test="${activation eq 'true'}">
                   <p><fmt:message key="msg.activation-true" bundle="${message}"/></p>
                   <button onclick="location.href='login'" class="log-reg-button"><fmt:message key="msg.login" bundle="${menu}"/></button>
             </c:if>
             <c:if test="${activation eq 'false'}">
                <p><fmt:message key="msg.activation-false" bundle="${message}"/></p>
             </c:if>
         </div>
    </div>
    </body>
</html>