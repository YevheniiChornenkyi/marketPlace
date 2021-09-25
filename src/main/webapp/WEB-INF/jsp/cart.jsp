<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${session.lang}"/>
<fmt:setBundle basename="message" var="message"/>
<fmt:setBundle basename="menu" var="menu"/>

<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/cart.css" rel="stylesheet">
    </head>
    <body>
        <div class="cart_section">
                <div class="row">
                    <div class="col-lg-10 offset-lg-1">
                            <div class="cart_title"><fmt:message key="msg.cart-label" bundle="${message}"/><small> (${cart.getItemsCount()} <fmt:message key="msg.item-in-cart" bundle="${message}"/>) </small></div>

                            <c:if test="${param.order eq 'true'}">
                                <p class="green-text"><fmt:message key="msg.order-accepted" bundle="${message}"/></p>
                            </c:if>

                            <c:forEach var="item" items="${cart.getCartEntrySet()}">
                                <div class="cart_items">
                                    <ul class="cart_list">
                                        <li class="cart_item clearfix">
                                            <div class="cart_item_image"><img class="preview" src="/image?id=${item.getKey().getImageName()}"></div>
                                            <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title"><fmt:message key="msg.name" bundle="${menu}"/></div>
                                                    <div class="cart_item_text">${item.getKey().getName()}</div>
                                                </div>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title"><fmt:message key="msg.model" bundle="${menu}"/></div>
                                                    <div class="cart_item_text">${item.getKey().getModel()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title"><fmt:message key="msg.description" bundle="${menu}"/></div>
                                                    <div class="cart_item_text">${item.getKey().getDescription()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title"><fmt:message key="msg.category" bundle="${menu}"/></div>
                                                    <div class="cart_item_text">${item.getKey().getCategoryName()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title"><fmt:message key="msg.manufacturer" bundle="${menu}"/></div>
                                                    <div class="cart_item_text">${item.getKey().getManufacturerName()}</div>
                                                </div>
                                               <div class="cart_item_color cart_info_col">
                                                   <div class="cart_item_title"><fmt:message key="msg.price" bundle="${menu}"/></div>
                                                   <div class="cart_item_text">${item.getKey().getPrice()}</div>
                                               </div>
                                               <div class="cart_item_color cart_info_col">
                                                   <div class="cart_item_title"><fmt:message key="msg.count" bundle="${menu}"/></div>
                                                   <div class="cart_item_text">${item.getValue()}</div>
                                               </div>
                                               <div class="cart_item_color cart_info_col">
                                                    <form action="/action/home-page" method="post">
                                                       <input type="hidden" value="up" name="count">
                                                       <input type="hidden" value="${item.getKey().getId()}" name="id">
                                                       <button type="submit" class="button_count">+1</button>
                                                    </form>
                                                    <form action="/action/home-page" method="post">
                                                       <input type="hidden" value="down" name="count">
                                                       <input type="hidden" value="${item.getKey().getId()}" name="id">
                                                       <button type="submit" class="button_count">-1</button>
                                                    </form>
                                                    <form action="/action/home-page" method="post">
                                                       <input type="hidden" value="delete" name="count">
                                                       <input type="hidden" value="${item.getKey().getId()}" name="id">
                                                       <button type="submit" class="button_count"><fmt:message key="msg.delete" bundle="${menu}"/></button>
                                                    </form>
                                               </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </c:forEach>


                            <div class="order_total">
                                <div class="order_total_content text-md-right">
                                    <div class="order_total_title"><fmt:message key="msg.order-total" bundle="${menu}"/></div>
                                    <div class="order_total_amount">${cart.cartTotalPrice()}&#8372</div>
                                </div>
                            </div>

                            <div class="container">

                                <form action="/action/orders" method="post">
                                    <div class="cart_buttons"> <button type="button" onclick="location.href='/home-page'" class="button cart_button_clear"><fmt:message key="msg.continue-shopping" bundle="${menu}"/></button>

                                    <label for="address"><b><fmt:message key="msg.address" bundle="${menu}"/></b></label>
                                    <input type="text" placeholder=<fmt:message key="msg.enter-delivery-address-place-holder" bundle="${menu}"/> name="address">

                                    <label for="phoneNumber"><b><fmt:message key="msg.phone-number" bundle="${menu}"/></b></label>
                                    <input type="text" placeholder=<fmt:message key="msg.phone-number-place-holder" bundle="${menu}"/> name="phoneNumber" required>

                                    <input type="hidden" value="newOrder" name="orderNote">
                                    <button type="submit" class="button cart_button_checkout"><fmt:message key="msg.confirm-order" bundle="${menu}"/></button> </div>
                                </form>
                            </div>
                    </div>

                </div>

        </div>

    </body>
</html>