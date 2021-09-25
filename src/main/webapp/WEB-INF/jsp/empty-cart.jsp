<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/empty-cart.css" rel="stylesheet">
    </head>
    <body>
        <div class="cart_section">
                <div class="row">
                    <div class="col-lg-10 offset-lg-1">
                            <div class="cart_title"><fmt:message key="msg.empty-cart" bundle="${message}"/></div>
                            <div class="cart_buttons"> <button type="button" onclick="location.href='/home-page'" class="button cart_button_clear"><fmt:message key="msg.continue-shopping" bundle="${menu}"/></button>

                    </div>
                </div>
        </div>
    </body>
</html>