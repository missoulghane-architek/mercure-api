package com.architek.mercure.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.architek.mercure.entities.Car;
import com.architek.mercure.entities.Image;
import com.architek.mercure.repositories.CarRepository;
import com.architek.mercure.repositories.ImageRepository;

import jakarta.transaction.Transactional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ImageRepository imageRepository;

    public CarService(CarRepository carRepository, ImageRepository imageRepository) {
        this.carRepository = carRepository;
        this.imageRepository = imageRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Optional<Car> getCarByUuid(UUID uuid) {
        return carRepository.findByUuidWithImages(uuid);
    }

    public Car saveCar(Car car) {
        car.setUuid(UUID.randomUUID());
        return carRepository.save(car);
    }

    @Transactional
    public Car saveCar(Car car, List<Image> images) {
        UUID uuid = UUID.randomUUID();
        car.setUuid(uuid);
        for (Image img : images) {
            car.addImage(img);
        }
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
