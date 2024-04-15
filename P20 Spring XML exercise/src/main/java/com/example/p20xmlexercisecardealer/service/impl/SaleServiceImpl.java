package com.example.p20xmlexercisecardealer.service.impl;

import com.example.p20xmlexercisecardealer.data.entities.Car;
import com.example.p20xmlexercisecardealer.data.entities.Customer;
import com.example.p20xmlexercisecardealer.data.entities.Sale;
import com.example.p20xmlexercisecardealer.data.repositories.CarRepository;
import com.example.p20xmlexercisecardealer.data.repositories.CustomerRepository;
import com.example.p20xmlexercisecardealer.data.repositories.SaleRepository;
import com.example.p20xmlexercisecardealer.service.SaleService;
import com.example.p20xmlexercisecardealer.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Sale sale = new Sale();
                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());
                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    private double getRandomDiscount() {
        return discounts.get(ThreadLocalRandom.current().nextInt(0, discounts.size()));
    }

    private Customer getRandomCustomer() {
        long customerId = ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1);
        return this.customerRepository.findById(customerId).get();
    }

    private Car getRandomCar() {
        long carId = ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1);
        return this.carRepository.findById(carId).get();
    }
}
