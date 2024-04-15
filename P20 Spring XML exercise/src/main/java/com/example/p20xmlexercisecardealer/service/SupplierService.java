package com.example.p20xmlexercisecardealer.service;

import jakarta.xml.bind.JAXBException;

public interface SupplierService {
    void seedSuppliers() throws JAXBException;

    void exportNonImporterSuppliers() throws JAXBException;
}
