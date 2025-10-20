package com.architek.mercure.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.architek.mercure.entities.Car;
import com.architek.mercure.entities.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class CarResponse {

    private UUID uuid;
    private String brand;
    private String model;
    private String mainImage;
    private List<String> images;

    public static CarResponse fromModel(Car car) {
        
        List<Image> carImages = Optional.ofNullable(car.getImages()).orElse(Collections.emptyList());

        Optional<Image> mainImg = carImages.stream().filter(i -> i.isMain()).findFirst();
        String mainImageUrl = mainImg.isEmpty() ? "" : mainImg.get().getPath();

        CarResponse response = CarResponse.builder()
            .uuid(car.getUuid())
            .brand(car.getBrand())
            .model(car.getModel())
            .mainImage("http://localhost:8080/api/v1/files/" + mainImageUrl)
            .images(carImages.stream().map(i -> "http://localhost:8080/api/v1/files/" + i.getPath()).collect(Collectors.toList()))
            .build();
        return response;
    }
}
