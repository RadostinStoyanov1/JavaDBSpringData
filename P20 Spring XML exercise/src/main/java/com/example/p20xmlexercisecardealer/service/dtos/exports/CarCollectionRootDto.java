package com.example.p20xmlexercisecardealer.service.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarCollectionRootDto implements Serializable {
    @XmlElement(name = "car")
    List<CarCollectionDto> carCollectionDtoList;

    public List<CarCollectionDto> getCarCollectionDtoList() {
        return carCollectionDtoList;
    }

    public void setCarCollectionDtoList(List<CarCollectionDto> carCollectionDtoList) {
        this.carCollectionDtoList = carCollectionDtoList;
    }
}
