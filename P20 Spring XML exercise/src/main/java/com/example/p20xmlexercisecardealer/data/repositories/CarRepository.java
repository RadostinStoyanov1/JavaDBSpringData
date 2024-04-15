package com.example.p20xmlexercisecardealer.data.repositories;

import com.example.p20xmlexercisecardealer.data.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("FROM Car c WHERE c.make LIKE :make ORDER BY c.model, c.travelledDistance DESC")
    Set<Car> findAllByMakeOrderByModelAndTravelledDistanceDesc(String make);

    Set<Car> findAllBy();
}
