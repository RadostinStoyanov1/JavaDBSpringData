package com.example.p20xmlexercisecardealer.data.repositories;

import com.example.p20xmlexercisecardealer.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Set<Customer> findAllByOrderByBirthDateAscIsYoungDriverAsc();
}
