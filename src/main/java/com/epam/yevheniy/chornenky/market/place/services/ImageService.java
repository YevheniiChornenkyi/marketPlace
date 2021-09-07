package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.FileNotFoundException;
import com.epam.yevheniy.chornenky.market.place.repositories.ImageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class ImageService {
    private static final String NO_ICON_NAME = "no-icon.png";
    private final String ICON_HOME_DIRECTORY_PATH;
    private final ImageRepository imageRepository;

    public ImageService(String ICON_HOME_DIRECTORY_PATH, ImageRepository imageRepository) {
        this.ICON_HOME_DIRECTORY_PATH = ICON_HOME_DIRECTORY_PATH;
        this.imageRepository = imageRepository;
    }

    public byte[] getImageById(String imageId) {
        String imageName = Optional.ofNullable(imageId)
                .map(checkedImageId -> imageRepository.findImageNameById())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .orElse(NO_ICON_NAME);

        String pathToIcon = ICON_HOME_DIRECTORY_PATH + imageName;
        try {
            File file = new File(pathToIcon);
            return new FileInputStream(file).readAllBytes();
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
