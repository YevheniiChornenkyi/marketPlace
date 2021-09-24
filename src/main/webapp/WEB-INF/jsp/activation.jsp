<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/activation.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
         <div class="text">
             <c:if test="${activation eq 'true'}">
                   <p>Successful activation now you can login</p>
                   <button onclick="location.href='login'" class="log-reg-button">LOGIN</button>
             </c:if>
             <c:if test="${activation eq 'false'}">
                <p>This key already used.</p>
             </c:if>
         </div>
    </div>
    </body>
</html>