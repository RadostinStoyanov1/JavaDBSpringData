package com.example.p20xmlexercisecardealer.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierRootDto implements Serializable {
    @XmlElement(name = "supplier")
    private List<SupplierDto> supplierDtoList;

    public List<SupplierDto> getSupplierDtoList() {
        return supplierDtoList;
    }

    public void setSupplierDtoList(List<SupplierDto> supplierDtoList) {
        this.supplierDtoList = supplierDtoList;
    }
}
