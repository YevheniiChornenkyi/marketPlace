package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.repositories.GoodsRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.CategoryEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.ManufacturerEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.*;

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
        String name = goodsEntity.getName();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        String categoryName = goodsEntity.getCategory().getName();
        String imageName = goodsEntity.getImageName();
        String description = goodsEntity.getDescription();
        String manufacturerName = goodsEntity.getManufacturer().getName();
        return new GoodsViewDTO(id, model, price, categoryName, imageName, description, manufacturerName, name);
    }

    public void createGoods(CreateGoodsDTO createGoodsDTO) {
        String imageName = null;
        int categoryId = createGoodsDTO.getCategory();
        int manufacturerId = createGoodsDTO.getManufacturer();
        categoryAndManufacturerValidate(categoryId, manufacturerId);

        if (Objects.nonNull(createGoodsDTO.getImage())) {
            imageName = imageService.saveImage(createGoodsDTO.getImage());
        }

        String name = createGoodsDTO.getName();
        String model = createGoodsDTO.getModel();
        String price = createGoodsDTO.getPrice();
        CategoryEntity category = new CategoryEntity(categoryId, null);
        String description = createGoodsDTO.getDescription();
        ManufacturerEntity manufacturer = new ManufacturerEntity(manufacturerId, null);

        GoodsEntity goodsEntity = new GoodsEntity(name, model, null, price, category, imageName, description, manufacturer, null);
        goodsRepository.createGoods(goodsEntity);
    }

    public void editGoods(EditGoodsDTO editGoodsDTO) {
        int category = editGoodsDTO.getCategory();
        int manufacturer = editGoodsDTO.getManufacturer();

        categoryAndManufacturerValidate(category, manufacturer);

        String name = editGoodsDTO.getName();
        String model = editGoodsDTO.getModel();
        String price = editGoodsDTO.getPrice();
        String description = editGoodsDTO.getDescription();
        byte[] imageBytes = editGoodsDTO.getImage();
        String imageName;
        String oldImageName = editGoodsDTO.getOldImageName();
        int id = editGoodsDTO.getId();
        if (Objects.nonNull(imageBytes)) {
            imageName = imageService.saveImage(imageBytes);
            if (!oldImageName.equals("null")) {
                imageService.deleteImageByName(oldImageName);
            }
        } else {
            imageName = oldImageName;
        }
        CategoryEntity categoryEntity = new CategoryEntity(category, null);
        ManufacturerEntity manufacturerEntity = new ManufacturerEntity(manufacturer, null);
        GoodsEntity goodsEntity = new GoodsEntity(name, model, id, price, categoryEntity,
                imageName, description, manufacturerEntity, null);

        goodsRepository.editGoods(goodsEntity);
    }

    private void categoryAndManufacturerValidate(int category, int manufacturer) {
        Optional<CategoryEntity> categoryEntityOptional = goodsRepository.findCategoryById(category);
        Optional<ManufacturerEntity> manufacturerEntityOptional = goodsRepository.findManufacturerById(manufacturer);

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

    public List<CategoryDto> getCategoriesDtoList() {
        List<CategoryEntity> categoriesListEntity = goodsRepository.findAllCategories();
        return categoriesListEntity.stream()
                .map(this::categoryEntityToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto categoryEntityToDto(CategoryEntity categoryEntity) {
        int id = categoryEntity.getId();
        String name = categoryEntity.getName();
        return new CategoryDto(id, name);
    }

    public List<ManufacturerDto> getManufacturersDtoList() {
        List<ManufacturerEntity> manufacturersListEntity = goodsRepository.findAllManufacturers();
        return manufacturersListEntity.stream()
                .map(this::manufacturerEntityToDto)
                .collect(Collectors.toList());
    }

    private ManufacturerDto manufacturerEntityToDto(ManufacturerEntity manufacturerEntity) {
        int id = manufacturerEntity.getId();
        String name = manufacturerEntity.getName();
        return new ManufacturerDto(id, name);
    }

    public Optional<GoodsViewDTO> getById(String goodsId) {
        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findGoodsById(goodsId);
        return goodsEntityOptional.map(this::mapToDTO);
    }
}

