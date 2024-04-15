package com.example.p20xmlexercisecardealer.service.impl;

import com.example.p20xmlexercisecardealer.data.entities.Customer;
import com.example.p20xmlexercisecardealer.data.repositories.CustomerRepository;
import com.example.p20xmlexercisecardealer.service.CustomerService;
import com.example.p20xmlexercisecardealer.service.dtos.exports.CustomerOrderedDto;
import com.example.p20xmlexercisecardealer.service.dtos.exports.CustomerOrderedRootDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.CustomerSeedDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.CustomerSeedRootDto;
import com.example.p20xmlexercisecardealer.util.ValidationUtil;
import com.example.p20xmlexercisecardealer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String FILE_PATH = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\imports\\customers.xml";
    private static final String FILE_EXPORT_ORDERED_CUSTOMERS = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\exports\\ordered-customers.xml";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        if (this.customerRepository.count() == 0) {
            CustomerSeedRootDto customerSeedRootDto = xmlParser.parse(CustomerSeedRootDto.class, FILE_PATH);
            for (CustomerSeedDto customerSeedDto : customerSeedRootDto.getCustomerSeedDtoList()) {
                if (!this.validationUtil.isValid(customerSeedDto)) {
                    System.out.println("Invalid car data");
                    continue;
                }
                Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
                this.customerRepository.saveAndFlush(customer);
            }
        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedDto> customerOrderedDtoList = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedDto.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDto customerOrderedRootDto = new CustomerOrderedRootDto();
        customerOrderedRootDto.setCustomerOrderedDtoList(customerOrderedDtoList);

        this.xmlParser.exportToFile(CustomerOrderedRootDto.class, customerOrderedRootDto, FILE_EXPORT_ORDERED_CUSTOMERS);
    }
}
