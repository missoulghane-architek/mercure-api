package com.architek.mercure.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.architek.mercure.entities.Image;
import com.architek.mercure.repositories.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public Image saveImage(Image car) {
        return imageRepository.save(car);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
