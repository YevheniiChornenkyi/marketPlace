<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/users.css" rel="stylesheet">
    </head>
    <body>
        <div class="cart_section">
            <div class="row">
                <div class="col-lg-10 offset-lg-1">

                    <c:forEach var="item" items="${usersDtoList}">
                        <div class="cart_items">
                            <ul class="cart_list">
                                <li class="cart_item clearfix">
                                    <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Name</div>
                                            <div class="cart_item_text">${item.getName()}</div>
                                        </div>
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Surname</div>
                                            <div class="cart_item_text">${item.getSurName()}</div>
                                        </div>
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Email</div>
                                            <div class="cart_item_text">${item.getEmail()}</div>
                                        </div>
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Role</div>
                                            <div class="cart_item_text">${item.getRole()}</div>
                                        </div>
                                        <div class="cart_item_name cart_info_col">
                                            <div class="cart_item_title">Status</div>
                                            <div class="cart_item_text">${item.getIsActive()}</div>
                                        </div>
                                        <c:choose>
                                            <c:when test="${item.getIsActive() eq 'true'}">
                                                <form action="users" method="post">
                                                   <input type="hidden" value="ban" name="is_active">
                                                   <input type="hidden" value="${item.getId()}" name="userId">
                                                   <button type="submit" class="button_ban">BLOCK</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="users" method="post">
                                                   <input type="hidden" value="unBan" name="is_active">
                                                   <input type="hidden" value="${item.getId()}" name="userId">
                                                   <button type="submit" class="button_ban">UNBLOCK</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>