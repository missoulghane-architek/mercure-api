package com.architek.mercure.controllers;

import com.architek.mercure.entities.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class CarRequestCreate {

    private String brand;
    private String model;

    public static Car toModel(CarRequestCreate request) {
        return Car.builder()
                    .brand(request.brand)
                    .model(request.model)
                    .build();
    }

}
