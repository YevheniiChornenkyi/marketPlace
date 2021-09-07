package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.repositories.GoodsRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDto;

import java.util.List;
import java.util.stream.Collectors;

public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public List<GoodsViewDto> getGoodsViewDTOList() {
        return goodsRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GoodsViewDto mapToDTO(GoodsEntity goodsEntity) {
        int id = goodsEntity.getId();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        String categoryName = goodsEntity.getCategory().getName();
        String iconPath = goodsEntity.getImageId();
        String description = goodsEntity.getDescription();
        String manufacturerName = goodsEntity.getManufacturer().getName();
        return new GoodsViewDto(id, model, price, categoryName, iconPath, description, manufacturerName);
    }
}
