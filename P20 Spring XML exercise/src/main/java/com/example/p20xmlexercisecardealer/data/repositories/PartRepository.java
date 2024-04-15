package com.example.p20xmlexercisecardealer.data.repositories;

import com.example.p20xmlexercisecardealer.data.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
