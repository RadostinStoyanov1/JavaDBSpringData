package com.example.p20xmlexercisecardealer.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedRootDto implements Serializable {

    @XmlElement(name = "customer")
    private List<CustomerOrderedDto> customerOrderedDtoList;

    public List<CustomerOrderedDto> getCustomerOrderedDtoList() {
        return customerOrderedDtoList;
    }

    public void setCustomerOrderedDtoList(List<CustomerOrderedDto> customerOrderedDtoList) {
        this.customerOrderedDtoList = customerOrderedDtoList;
    }
}
