<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <link href="/static/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="/static/css/cart.css" rel="stylesheet">
    </head>
    <body>
        <div class="cart_section">
                <div class="row">
                    <div class="col-lg-10 offset-lg-1">
                            <div class="cart_title">Shopping Cart<small> (${cart.getItemsCount()} item in your cart) </small></div>

                            <c:forEach var="item" items="${cart.getCartEntrySet()}">
                                <div class="cart_items">
                                    <ul class="cart_list">
                                        <li class="cart_item clearfix">
                                            <div class="cart_item_image"><img class="preview" src="/image?id=${item.getKey().getImageName()}"></div>
                                            <div class="cart_item_info d-flex flex-md-row flex-column justify-content-between">
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Name</div>
                                                    <div class="cart_item_text">${item.getKey().getName()}</div>
                                                </div>
                                                <div class="cart_item_name cart_info_col">
                                                    <div class="cart_item_title">Model</div>
                                                    <div class="cart_item_text">${item.getKey().getModel()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title">Description</div>
                                                    <div class="cart_item_text">${item.getKey().getDescription()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title">Category</div>
                                                    <div class="cart_item_text">${item.getKey().getCategoryName()}</div>
                                                </div>
                                                <div class="cart_item_color cart_info_col">
                                                    <div class="cart_item_title">Manufacturer</div>
                                                    <div class="cart_item_text">${item.getKey().getManufacturerName()}</div>
                                                </div>
                                               <div class="cart_item_color cart_info_col">
                                                   <div class="cart_item_title">Price</div>
                                                   <div class="cart_item_text">${item.getKey().getPrice()}</div>
                                               </div>
                                               <div class="cart_item_color cart_info_col">
                                                   <div class="cart_item_title">Count</div>
                                                   <div class="cart_item_text">${item.getValue()}</div>
                                               </div>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </c:forEach>


                            <div class="order_total">
                                <div class="order_total_content text-md-right">
                                    <div class="order_total_title">Order Total:</div>
                                    <div class="order_total_amount">${cart.cartTotalPrice()}&#8372</div>
                                </div>
                            </div>
                            <div class="cart_buttons"> <button type="button" onclick="location.href='/home-page'" class="button cart_button_clear">Continue Shopping</button>
                             <button type="button"  class="button cart_button_checkout">Confirm order</button> </div>
                    </div>
                </div>
        </div>
    </body>
</html>