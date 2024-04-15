package com.example.p20xmlexercisecardealer.data.repositories;

import com.example.p20xmlexercisecardealer.data.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    @Query("FROM Supplier s WHERE s.isImporter = false")
    Set<Supplier> findAllByImporterIsFalse();
}
