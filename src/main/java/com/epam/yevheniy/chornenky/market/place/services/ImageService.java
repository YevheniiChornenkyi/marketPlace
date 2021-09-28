package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.FileCreationException;
import com.epam.yevheniy.chornenky.market.place.exceptions.FileDeleteException;
import com.epam.yevheniy.chornenky.market.place.exceptions.FileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Service save accepted image to directory obtained in constructor
 */
public class ImageService {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);
    private static final String NO_ICON_NAME = "no-icon.png";

    private final String ICON_HOME_DIRECTORY_PATH;

    public ImageService(String ICON_HOME_DIRECTORY_PATH) {
        this.ICON_HOME_DIRECTORY_PATH = ICON_HOME_DIRECTORY_PATH;
    }

    /**
     * Return image in byte whose name he received in parameter or default image if file with specified name does not exist
     * @param imageName String image name
     * @return byte[] image in byte
     */
    public byte[] getImageById(String imageName) {

        return Optional.ofNullable(imageName)
                .map(image -> ICON_HOME_DIRECTORY_PATH + image)
                .map(this::findFileInImagesFolderById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::fileToByteArray)
                .orElseGet(this::getDefaultImageBytes);
    }

    /**
     * Will return the file stored in the specified directory
     * @param imageFilePath path to file
     * @return option of file. empty if file does not exist
     */
    private Optional<File> findFileInImagesFolderById(String imageFilePath) {
        File file = new File(imageFilePath);
        if (file.exists()) {
            return Optional.of(file);
        }
        return Optional.empty();
    }

    /**
     * try turn received file in bytes. Return default byte[] if cant
     * @param image file
     * @return image to bytes
     */
    private byte[] fileToByteArray(File image) {
        try {
            return Files.readAllBytes(image.toPath());
        } catch (IOException e) {
            return getDefaultImageBytes();
        }
    }

    /**
     * convert default image to bytes
     */
    private byte[] getDefaultImageBytes() {
        try {
            return Files.readAllBytes(new File(ICON_HOME_DIRECTORY_PATH + NO_ICON_NAME).toPath());
        } catch (IOException e) {
            LOGGER.error("Cannot convert local no-icon image to byte array", e);
            throw new FileNotFoundException();
        }
    }

    /**
     * save image to default directory. if it has exception return default file name
     * @param image image in bytes
     * @return saved file name
     */
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

    /**
     * tru to save received file to default directory
     * @param image file path to save
     * @param bytesImage image in bytes
     * @return file name or throw exception
     */
    private String writeImage(File image, byte[] bytesImage) {
        try(OutputStream outputStream = new FileOutputStream(image)) {
            outputStream.write(bytesImage);
            return image.getName();
        } catch (IOException e) {
            LOGGER.error("Problems with writing file when try save");
            throw new FileCreationException();
        }
    }

    /**
     * Delete file with received name
     * @param oldImageName file name
     */
    public void deleteImageByName(String oldImageName) {
        File file = new File(ICON_HOME_DIRECTORY_PATH + oldImageName);
        if (!file.delete()) {
            LOGGER.error(String.format("Cannot delete file at %s", ICON_HOME_DIRECTORY_PATH + oldImageName));
            throw new FileDeleteException();
        }
    }
}
