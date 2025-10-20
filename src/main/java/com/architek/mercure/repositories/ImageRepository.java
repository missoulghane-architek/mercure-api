package com.architek.mercure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.architek.mercure.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    
}

