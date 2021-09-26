<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/not-found.css" rel="stylesheet">
    </head>

    <body>
        <div class="text">
            <p><fmt:message key="msg.page-not-found"/>
            <br><br>
            <fmt:message key="msg.page-not-found-label"/></p>
        </div>
    </body>
</html>