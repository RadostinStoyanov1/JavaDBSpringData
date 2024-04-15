package com.example.p20xmlexercisecardealer.service;

import jakarta.xml.bind.JAXBException;

public interface CustomerService {
    void seedCustomers() throws JAXBException;

    void exportOrderedCustomers() throws JAXBException;
}
