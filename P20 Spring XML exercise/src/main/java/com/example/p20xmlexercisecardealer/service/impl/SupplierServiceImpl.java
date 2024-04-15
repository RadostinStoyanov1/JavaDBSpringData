package com.example.p20xmlexercisecardealer.service.impl;

import com.example.p20xmlexercisecardealer.data.entities.Supplier;
import com.example.p20xmlexercisecardealer.data.repositories.SupplierRepository;
import com.example.p20xmlexercisecardealer.service.SupplierService;
import com.example.p20xmlexercisecardealer.service.dtos.exports.SupplierDto;
import com.example.p20xmlexercisecardealer.service.dtos.exports.SupplierRootDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.SupplierSeedDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.SupplierSeedRootDto;
import com.example.p20xmlexercisecardealer.util.ValidationUtil;
import com.example.p20xmlexercisecardealer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String FILE_PATH = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\imports\\suppliers.xml";
    private static final String LOCAL_SUPPLIERS_XML_PATH = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\exports\\local-suppliers.xml";

    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSuppliers() throws JAXBException {
        if (this.supplierRepository.count() == 0) {
            SupplierSeedRootDto supplierSeedRootDto = xmlParser.parse(SupplierSeedRootDto.class, FILE_PATH);
            for (SupplierSeedDto supplierSeedDto : supplierSeedRootDto.getSuppliers()) {
                if (!this.validationUtil.isValid(supplierSeedDto)) {
                    System.out.println("Invalid supplier data");
                    continue;
                }

                Supplier supplier = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplier);
            }
        }
    }

    @Override
    public void exportNonImporterSuppliers() throws JAXBException {
        List<SupplierDto> supplierDtoList = this.supplierRepository.findAllByImporterIsFalse()
                .stream()
                .map(s -> {
                    int size = s.getParts().size();
                    SupplierDto supplierDto = this.modelMapper.map(s, SupplierDto.class);
                    supplierDto.setPartsCount(size);
                    return supplierDto;
                })
                .collect(Collectors.toList());

        SupplierRootDto supplierRootDto = new SupplierRootDto();
        supplierRootDto.setSupplierDtoList(supplierDtoList);
        xmlParser.exportToFile(SupplierRootDto.class, supplierRootDto, LOCAL_SUPPLIERS_XML_PATH);
    }
}
