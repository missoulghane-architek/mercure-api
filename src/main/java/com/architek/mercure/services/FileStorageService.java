package com.architek.mercure.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

    private final Path storageDir = Paths.get("/Users/morad/architek-consulting/s3-local/mercure-bucket/");

    public String store(MultipartFile file) {

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (originalFilename.contains("..")) {
                throw new IllegalArgumentException("Nom de fichier invalide : " + originalFilename);
            }
            
            Path targetLocation = storageDir.resolve(originalFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return originalFilename;

        } catch (IOException ex) {
            throw new RuntimeException("Impossible de sauvegarder le fichier : " + originalFilename, ex);
        }
    }

    public List<String> storeAll(List<MultipartFile> files) {
        return files.stream().map(this::store).toList();
    }
}
