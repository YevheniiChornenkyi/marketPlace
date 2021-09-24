<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/orders.css" rel="stylesheet">
    </head>
    <body>
        <div class="cart_section">
                <div class="row">
                    <div class="col-lg-10 offset-lg-1">
                            <div class="cart_title">Order list </small></div>

                            <c:forEach var="item" items="${orderViewDTOList}">
                                <div class="cart_items">
                                    <ul class="cart_list">
                                        <li class="cart_item clearfix">
                                            <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                                <c:if test="${authentication.getName() eq 'ADMIN'}">
                                                    <div class="cart_item_name cart_info_col">
                                                        <div class="cart_item_title">Order id</div>
                                                        <div class="cart_item_text">${item.getOrderId()}</div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${authentication.getName() eq 'ADMIN'}">
                                                    <div class="cart_item_name cart_info_col">
                                                        <div class="cart_item_title">User id</div>
                                                        <div class="cart_item_text">${item.getUserId()}</div>
                                                    </div>
                                                </c:if>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Status</div>
                                                    <div class="cart_item_text">${item.getStatus()}</div>
                                                </div>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Address</div>
                                                    <div class="cart_item_text">${item.getAddress()}</div>
                                                </div>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Phone number</div>
                                                    <div class="cart_item_text">${item.getPhoneNumber()}</div>
                                                </div>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Price</div>
                                                    <div class="cart_item_text">${item.getPrice()}</div>
                                                </div>
                                                <form action"/action/orders" method="post">
                                                    <c:if test="${authentication.getRole() eq 'ADMIN'}">
                                                        <select class="select-css" id="status" name="status">
                                                            <c:forEach var="status" items="${statuses}">
                                                                <c:choose>
                                                                    <c:when test="${status.getStatusView() eq item.getStatus()}">
                                                                        <option value="${status.toString()}" selected="${status.toString()}">${status.getStatusView()}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option value="${status.toString()}">${status.getStatusView()}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="hidden" value="newStatus" name="orderNote">
                                                        <input type="hidden" name="orderId" value="${item.getOrderId()}" required>
                                                        <button type="submit" class="status-button">Change status</button>

                                                    </c:if>
                                                </form>
                                            </div>
                                        </li>
                                        <c:set var="dtoList" scope="page" value="${item.getOrderItemDTOList()}"/>
                                        <c:forEach var="orderItem" items="${dtoList}">
                                            <div class="cart_items">
                                                <ul class="cart_list">
                                                    <li class="cart_item clearfix">
                                                        <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">

                                                            <div class="cart_item_name cart_info_col">
                                                                <div class="cart_item_title">Goods id</div>
                                                                <div class="cart_item_text">${orderItem.getGoodsId()}</div>
                                                            </div>
                                                            <div class="cart_item_name cart_info_col">
                                                                <div class="cart_item_title">Quantity</div>
                                                                <div class="cart_item_text">${orderItem.getQuantity()}</div>
                                                            </div>
                                                            <div class="cart_item_name cart_info_col">
                                                                <div class="cart_item_title">Unit price</div>
                                                                <div class="cart_item_text">${orderItem.getPrice()}</div>
                                                            </div>
                                                            <div class="cart_item_name cart_info_col">
                                                                <div class="cart_item_title">Total price</div>
                                                                <div class="cart_item_text">${orderItem.getTotalPrice()}</div>
                                                            </div>

                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:forEach>
                    </div>
                </div>
        </div>
    </body>
</html>






