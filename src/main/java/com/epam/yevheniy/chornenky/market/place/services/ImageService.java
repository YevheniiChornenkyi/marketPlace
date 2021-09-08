package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.FileNotFoundException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class ImageService {

    private static final Logger LOGGER = Logger.getLogger(ImageService.class);
    private static final String NO_ICON_NAME = "no-icon.png";

    private final String ICON_HOME_DIRECTORY_PATH;

    public ImageService(String ICON_HOME_DIRECTORY_PATH) {
        this.ICON_HOME_DIRECTORY_PATH = ICON_HOME_DIRECTORY_PATH;
    }

    public byte[] getImageById(String imageName) {

        return Optional.ofNullable(imageName)
                .map(image -> ICON_HOME_DIRECTORY_PATH + image)
                .map(this::findFileInImagesFolderById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::fileToByteArray)
                .orElseGet(this::getDefaultImageBytes);
    }

    private Optional<File> findFileInImagesFolderById(String imageFilePath) {
        File file = new File(imageFilePath);
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private byte[] fileToByteArray(File image) {
        try {
            return Files.readAllBytes(image.toPath());
        } catch (IOException e) {
            return getDefaultImageBytes();
        }
    }

    private byte[] getDefaultImageBytes() {
        try {
            return Files.readAllBytes(new File(ICON_HOME_DIRECTORY_PATH + NO_ICON_NAME).toPath());
        } catch (IOException e) {
            LOGGER.error("Cannot convert local no-icon image to byte array", e);
            throw new FileNotFoundException();
        }
    }

    public String saveImage(byte[] image) {
        return NO_ICON_NAME;
    }
}
