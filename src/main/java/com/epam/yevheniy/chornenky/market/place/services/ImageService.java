package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.FileCreationException;
import com.epam.yevheniy.chornenky.market.place.exceptions.FileNotFoundException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
        if (Objects.nonNull(image)) {
            String fileName = UUID.randomUUID().toString();
            try {
                File file = new File(ICON_HOME_DIRECTORY_PATH + fileName);
                return file.createNewFile() ? writeImage(file, image) : NO_ICON_NAME;
            } catch (IOException e) {
                LOGGER.error(String.format("Cannot create file at %s", ICON_HOME_DIRECTORY_PATH + fileName), e);
                throw new FileCreationException();
            }
        }
        return NO_ICON_NAME;
    }

    private String writeImage(File image, byte[] bytesImage) {
        try(OutputStream outputStream = new FileOutputStream(image)) {
            outputStream.write(bytesImage);
            return image.getName();
        } catch (IOException e) {
            LOGGER.error("Problems with writing file when try save");
            throw new FileCreationException();
        }
    }
}
