package com.example.p20xmlexercisecardealer.service.dtos.imports;

import com.example.p20xmlexercisecardealer.data.entities.Supplier;
import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierSeedRootDto implements Serializable {

    @XmlElement(name = "supplier")
    private List<SupplierSeedDto> suppliers;

    public List<SupplierSeedDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierSeedDto> suppliers) {
        this.suppliers = suppliers;
    }
}
