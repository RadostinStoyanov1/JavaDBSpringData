package com.example.p20xmlexercisecardealer.data.repositories;

import com.example.p20xmlexercisecardealer.data.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
