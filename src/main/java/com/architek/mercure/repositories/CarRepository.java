package com.architek.mercure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.architek.mercure.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.images WHERE c.uuid = :uuid")
    Optional<Car> findByUuidWithImages(@Param("uuid") UUID uuid);

    Optional<Car> findByUuid(UUID uuid);
    
}
