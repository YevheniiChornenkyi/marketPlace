package com.epam.yevheniy.chornenky.market.place.models;

import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * the object responsible for the operations with the cart.
 * Keep purchases in the MAP. Key - goods. Value - quantity
 */
public class Cart {
    private static final Logger LOGGER = LogManager.getLogger(Cart.class);
    private final Map<GoodsViewDTO, Integer> cart = new HashMap<>();

    /**
     * add goods to cart or increase quantity by +1
     * @param goodsViewDTO all information about goods(id, model, price, description, category, manufacturer, imageName, date of created)
     */
    public void addToCart(GoodsViewDTO goodsViewDTO) {
        cart.computeIfPresent(goodsViewDTO, (item, count) -> count + 1);
        cart.putIfAbsent(goodsViewDTO, 1);
        LOGGER.info("Added new item to cart now cart have {} items. Added item parameters: {}", getGoodsCount(), goodsViewDTO);
    }

    /**
     * increase goods count by 1 (value of map)
     * @param id goods id
     */
    public void increaseGoodsCount(int id) {
        Optional<GoodsViewDTO> goodsViewDTOOptional = findIfPresent(id);
        goodsViewDTOOptional.ifPresent(goodsDTO -> cart.computeIfPresent(goodsDTO, (item, count) -> count + 1));
    }

    /**
     * decrease goods count by 1 (value of map)
     * @param id goods id
     */
    public void decreaseGoodsCount(int id) {
        Optional<GoodsViewDTO> goodsViewDTOOptional = findIfPresent(id);
        goodsViewDTOOptional.ifPresent((goodsDto) -> {
            cart.computeIfPresent(goodsDto, (item, count) -> count - 1);
            if (cart.get(goodsDto) <= 0) {
                cart.remove(goodsDto);
            }
        });
    }

    /**
     * delete position in MAP by id
     * @param id goods id
     */
    public void deleteGoods(int id) {
        Optional<GoodsViewDTO> goodsViewDTOOptional = findIfPresent(id);
        goodsViewDTOOptional.ifPresent(cart::remove);
    }

    private Optional<GoodsViewDTO> findIfPresent(int id) {
        return cart.keySet().stream()
                .filter(goodsViewDTO -> Objects.equals(id, goodsViewDTO.getId()))
                .findAny();
    }

    /**
     *Return sum of all purchases in cart. uses in cart.jsp
     * @return sum all purchases (String)
     */
    public String cartTotalPrice() {
        Set<Map.Entry<GoodsViewDTO, Integer>> goods = cart.entrySet();
        BigDecimal result = new BigDecimal(0);
        for (Map.Entry<GoodsViewDTO, Integer> entry : goods) {
            BigDecimal count = new BigDecimal(entry.getValue());
            BigDecimal singleItemPrice = new BigDecimal(entry.getKey().getPrice());
            BigDecimal rowPrice = count.multiply(singleItemPrice);
            result = result.add(rowPrice);
        }
        return result.toString();
    }

    public Set<Map.Entry<GoodsViewDTO, Integer>> getCartEntrySet() {
        return cart.entrySet();
    }

    public int getGoodsCount() {
        return cart.size();
    }

    public int getItemsCount() {
        Collection<Integer> values = cart.values();
        int result = 0;
        for (int count : values) {
            result += count;
        }
        return result;
    }
}
