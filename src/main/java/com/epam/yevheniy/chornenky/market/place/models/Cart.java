package com.epam.yevheniy.chornenky.market.place.models;

import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private static final Logger LOGGER = LogManager.getLogger(Cart.class);
    private final Map<GoodsViewDTO, Integer> cart = new HashMap<>();

    public void addToCart(GoodsViewDTO goodsViewDTO) {
        cart.computeIfPresent(goodsViewDTO, (item, count) -> count + 1);
        cart.putIfAbsent(goodsViewDTO, 1);
        LOGGER.info("Added new item to cart now cart have {} items. Added item parameters: {}", getGoodsCount(), goodsViewDTO);
    }

    public void decreaseGoodsCount(int id) {
        Optional<GoodsViewDTO> goodsViewDTOOptional = findIfPresent(id);
        goodsViewDTOOptional.ifPresent((goodsDto) -> {
            cart.computeIfPresent(goodsDto, (item, count) -> count--);
            if (cart.get(goodsDto) <= 0) {
                cart.remove(goodsDto);
            }
        });
    }

    private Optional<GoodsViewDTO> findIfPresent(int id) {
        return cart.keySet().stream()
                .filter(goodsViewDTO -> Objects.equals(id, goodsViewDTO.getId()))
                .findAny();
    }

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
