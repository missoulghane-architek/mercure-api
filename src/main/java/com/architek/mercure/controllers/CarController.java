package com.architek.mercure.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.architek.mercure.entities.Car;
import com.architek.mercure.entities.Image;
import com.architek.mercure.services.CarService;
import com.architek.mercure.services.FileStorageService;
import com.architek.mercure.services.ImageService;

@RestController
@RequestMapping("/api/v1/cars")
@CrossOrigin(origins = "*") 
public class CarController {

    private final CarService carService;
    private final ImageService imageService;
    private final FileStorageService fileStorageService;

    public CarController(CarService carService, ImageService imageService, FileStorageService fileStorageService) {
        this.carService = carService;
        this.imageService = imageService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<CarResponse> getAllCars() {
        return carService.getAllCars().stream().map(c -> CarResponse.fromModel(c)).collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable UUID uuid) {
        return carService.getCarByUuid(uuid)    
                .map(car -> ResponseEntity.ok(CarResponse.fromModel(car)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarResponse>  createCar(@RequestPart("car") CarRequestCreate car, @RequestPart("mainImage") MultipartFile mainImage, @RequestPart("images") List<MultipartFile> images) {
        
        // Prepare car
        Car carCreated = CarRequestCreate.toModel(car);
        // Prepare Images
        List<Image> imgs = new ArrayList<>();

        imgs.add(Image.builder().main(true).path(mainImage.getOriginalFilename()).build());
        for (MultipartFile img : images) {
            imgs.add(Image.builder().main(false).path(img.getOriginalFilename()).build());

        }
        carCreated = carService.saveCar(carCreated, imgs);
        carCreated = carService.getCarByUuid(carCreated.getUuid()).get();
        
        return ResponseEntity.ok(CarResponse.fromModel(carCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carService.getCarById(id)
                .map(car -> {
                    //car.setModel(carDetails.getModel());
                    //car.setYear(carDetails.getYear());
                    return ResponseEntity.ok(carService.saveCar(car));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        if (carService.getCarById(id).isPresent()) {
            carService.deleteCar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}