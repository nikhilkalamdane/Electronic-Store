package com.nikhilkalamdane.electronic.store.services.impl;

import com.nikhilkalamdane.electronic.store.exceptions.BadApiRequestException;
import com.nikhilkalamdane.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Implementation of the FileService interface for handling file-related operations.
 */
@Service
public class FileServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFileName = file.getOriginalFilename();

        logger.info("Uploading file: {}", originalFileName);

        String fileExtension = getFileExtension(originalFileName);
        String generatedFileName = generateUniqueFileName(fileExtension);
        String fullPathWithFileName = Paths.get(path, generatedFileName).toString();

        validateAndSaveImage(fileExtension, path, generatedFileName, file.getInputStream());

        logger.info("File uploaded successfully: {}", generatedFileName);

        return generatedFileName;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = Paths.get(path, name).toString();

        logger.info("Retrieving resource: {}", fullPath);

        return new FileInputStream(fullPath);
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private String generateUniqueFileName(String fileExtension) {
        return UUID.randomUUID().toString() + fileExtension;
    }

    private void validateAndSaveImage(String fileExtension, String path, String generatedFileName, InputStream inputStream) throws IOException {
        String[] allowedExtensions = { ".png", ".jpg", ".jpeg" };

        if (isValidExtension(fileExtension, allowedExtensions)) {
            logger.info("Valid file extension: {}", fileExtension);

            File folder = new File(path);

            if (!folder.exists()) {
                folder.mkdirs();
                logger.info("Created folder: {}", folder.getAbsolutePath());
            }

            Files.copy(inputStream, Paths.get(path, generatedFileName));

            logger.info("File saved: {}", generatedFileName);
        } else {
            logger.warn("Invalid file extension: {}", fileExtension);
            throw new BadApiRequestException("File with the extension " + fileExtension + " is not allowed.");
        }
    }

    private boolean isValidExtension(String extension, String[] allowedExtensions) {
        for (String allowedExtension : allowedExtensions) {
            if (allowedExtension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
