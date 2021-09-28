package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.models.SiteFilter;
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

    /**
     * Using parameters createGoodsDTO create new GoodsEntity and send to repository lvl.
     * Using imageService to save image accepted is createGoodsDTO
     * @param createGoodsDTO goods parameters
     */
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

    /**
     * Using editGoodsDTO to create new goodsEntity.
     * Using imageService to save image accepted is createGoodsDTO if createGoodsDTO.getImage not null
     * @param editGoodsDTO goods parameters
     */
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

    /**
     * check accepted category and manufacturer contains in database.
     * @param category goods category
     * @param manufacturer goods manufacturer
     */
    private void categoryAndManufacturerValidate(int category, int manufacturer) {
        Optional<CategoryEntity> categoryEntityOptional = goodsRepository.findCategoryById(category);
        Optional<ManufacturerEntity> manufacturerEntityOptional = goodsRepository.findManufacturerById(manufacturer);

        Map<String, String> validationMap = new HashMap<>();

        if (categoryEntityOptional.isEmpty()) {
            validationMap.put("category", "msg.category-does-not-exist");
        }
        if (manufacturerEntityOptional.isEmpty()) {
            validationMap.put("manufacturer", "msg.manufacturer-does-not-exist");
        }
        if (!validationMap.isEmpty()) {
            throw new ValidationException(validationMap);
        }
    }

    /**
     * accepted category entity from repository lvl and convert to CategoryDTO
     * @return all categories in List<CategoryDto> format
     */
    public List<CategoryDto> getCategoriesDtoList() {
        List<CategoryEntity> categoriesListEntity = goodsRepository.findAllCategories();
        return categoriesListEntity.stream()
                .map(this::categoryEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * convert categoryEntity to categoryDTO
     * @param categoryEntity categoryEntity
     * @return categoryDTO
     */
    private CategoryDto categoryEntityToDto(CategoryEntity categoryEntity) {
        int id = categoryEntity.getId();
        String name = categoryEntity.getName();
        return new CategoryDto(id, name);
    }

    /**
     * accepted manufacturer entities from repository lvl and convert to ManufacturerDTO
     * @return all manufacturers in List<ManufacturerDTO> format
     */
    public List<ManufacturerDTO> getManufacturersDtoList() {
        List<ManufacturerEntity> manufacturersListEntity = goodsRepository.findAllManufacturers();
        return manufacturersListEntity.stream()
                .map(this::manufacturerEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * convert manufacturerEntity to manufacturerDTO
     * @param manufacturerEntity manufacturerEntity
     * @return manufacturerDTO
     */
    private ManufacturerDTO manufacturerEntityToDto(ManufacturerEntity manufacturerEntity) {
        int id = manufacturerEntity.getId();
        String name = manufacturerEntity.getName();
        return new ManufacturerDTO(id, name);
    }

    /**
     * Convert accepted from repository lvl goodsEntity to GoodsDTO
     * return optional of goodsDTO. empty if it does not found
     * @param goodsId id for searching
     * @return Optional<GoodsViewDTO> empty if it does not found
     */
    public Optional<GoodsViewDTO> getById(String goodsId) {
        Optional<GoodsEntity> goodsEntityOptional = goodsRepository.findGoodsById(goodsId);
        return goodsEntityOptional.map(this::mapToDTO);
    }

    /**
     * Convert goodsEntity to goodsViewDTO
     * @param goodsEntity goodsEntity
     * @return goodsViewDTO
     */
    private GoodsViewDTO mapToDTO(GoodsEntity goodsEntity) {
        int id = goodsEntity.getId();
        String name = goodsEntity.getName();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        String categoryName = goodsEntity.getCategory().getName();
        String imageName = goodsEntity.getImageName();
        String description = goodsEntity.getDescription();
        String manufacturerName = goodsEntity.getManufacturer().getName();
        String created = goodsEntity.getCreated().toString();
        return new GoodsViewDTO(id, model, price, categoryName, imageName, description, manufacturerName, name, created);
    }

    /**
     * Accepted createSiteFilterDTO. Us createSiteFilterDTO parameters to create siteFilter.
     * asks the repository for a GoodsViewDTOList sorted using siteFilter
     * return accepted from repository lvl List<GoodsViewDTO>
     * @param createSiteFilterDTO createSiteFilterDTO contains parameter which the user specified
     * @return List<GoodsViewDTO>
     */
    public List<GoodsViewDTO> getSortedGoodsViewDTOList(CreateSiteFilterDTO createSiteFilterDTO) {
        SiteFilter.Builder siteFilterBuilder = SiteFilter.getBuilder();
        siteFilterBuilder
                .setSortedType(createSiteFilterDTO.getSort())
                .setOrder(createSiteFilterDTO.getOrder())
                .setMinPrice(createSiteFilterDTO.getMinPrice())
                .setMaxPrice(createSiteFilterDTO.getMaxPrice());

        if (Objects.nonNull(createSiteFilterDTO.getCategories()) && createSiteFilterDTO.getCategories().length > 0) {
            for (String category : createSiteFilterDTO.getCategories()) {
                siteFilterBuilder.addCategory(Integer.parseInt(category));
            }
        } else {
            List<CategoryDto> categoriesDtoList = getCategoriesDtoList();
            for (CategoryDto dto : categoriesDtoList) {
                siteFilterBuilder.addCategory(dto.getCategoryId());
            }
        }
        SiteFilter siteFilter = siteFilterBuilder.getSiteFilter();
        return goodsRepository.getSortedGoodsViewDtoList(siteFilter);
    }
}

