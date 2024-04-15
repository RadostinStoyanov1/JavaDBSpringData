package com.example.p20xmlexercisecardealer.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(name = "birth_date")
    private LocalDateTime birthDate;
    @Column
    private boolean isYoungDriver;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Sale> sales;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
