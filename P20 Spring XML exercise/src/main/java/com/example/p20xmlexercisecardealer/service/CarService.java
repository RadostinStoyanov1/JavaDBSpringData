package com.example.p20xmlexercisecardealer.service;

import jakarta.xml.bind.JAXBException;

public interface CarService {
    void seedCars() throws JAXBException;

    void exportCarsByGivenBrand(String brand) throws JAXBException;
    void exportCarsWithTheirParts() throws JAXBException;
}
