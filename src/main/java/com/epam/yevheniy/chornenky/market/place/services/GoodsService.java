package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.repositories.GoodsRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.CategoryEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.ManufacturerEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateGoodsDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final ImageService imageService;

    public GoodsService(GoodsRepository goodsRepository, ImageService imageService) {
        this.goodsRepository = goodsRepository;
        this.imageService = imageService;
    }

    public List<GoodsViewDTO> getGoodsViewDTOList() {
        return goodsRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GoodsViewDTO mapToDTO(GoodsEntity goodsEntity) {
        int id = goodsEntity.getId();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        String categoryName = goodsEntity.getCategory().getName();
        String imageName = goodsEntity.getImageName();
        String description = goodsEntity.getDescription();
        String manufacturerName = goodsEntity.getManufacturer().getName();
        return new GoodsViewDTO(id, model, price, categoryName, imageName, description, manufacturerName);
    }

    public void createGoods(CreateGoodsDTO createGoodsDTO) {
        validate(createGoodsDTO);
        String imageName = null;

        if (Objects.nonNull(createGoodsDTO.getImage())) {
            imageName = imageService.saveImage(createGoodsDTO.getImage());
        }

        String model = createGoodsDTO.getModel();
        String price = createGoodsDTO.getPrice();
        CategoryEntity category = new CategoryEntity(createGoodsDTO.getCategory(), null);
        String description = createGoodsDTO.getDescription();
        ManufacturerEntity manufacturer = new ManufacturerEntity(createGoodsDTO.getManufacturer(), null);

        GoodsEntity goodsEntity = new GoodsEntity(model, null, price, category, imageName, description, manufacturer, null);
        goodsRepository.createGoods(goodsEntity);
    }

    private void validate(CreateGoodsDTO createGoodsDTO) {
        Optional<CategoryEntity> categoryEntityOptional = goodsRepository.findCategoryById(createGoodsDTO.getCategory());
        Optional<ManufacturerEntity> manufacturerEntityOptional = goodsRepository.findManufacturerById(createGoodsDTO.getManufacturer());

        Map<String, String> validationMap = new HashMap<>();

        if (categoryEntityOptional.isEmpty()) {
            validationMap.put("category", "This category does not exist");
        }
        if (manufacturerEntityOptional.isEmpty()) {
            validationMap.put("manufacturer", "This category does not exist");
        }
        if (!validationMap.isEmpty()) {
            throw new ValidationException(validationMap);
        }
    }
}

