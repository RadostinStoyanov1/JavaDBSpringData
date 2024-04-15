package com.example.p20xmlexercisecardealer.service.impl;

import com.example.p20xmlexercisecardealer.data.entities.Part;
import com.example.p20xmlexercisecardealer.data.entities.Supplier;
import com.example.p20xmlexercisecardealer.data.repositories.PartRepository;
import com.example.p20xmlexercisecardealer.data.repositories.SupplierRepository;
import com.example.p20xmlexercisecardealer.service.PartService;
import com.example.p20xmlexercisecardealer.service.dtos.imports.PartSeedDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.PartSeedRootDto;
import com.example.p20xmlexercisecardealer.util.ValidationUtil;
import com.example.p20xmlexercisecardealer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {
    private static final String FILE_PATH = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\imports\\parts.xml";

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final SupplierRepository supplierRepository;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, SupplierRepository supplierRepository) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void seedParts() throws JAXBException {
        if (this.partRepository.count() == 0) {
            PartSeedRootDto partSeedRootDto = this.xmlParser.parse(PartSeedRootDto.class, FILE_PATH);
            for (PartSeedDto partSeedDto : partSeedRootDto.getPartsList()) {
                if (!this.validationUtil.isValid(partSeedDto)) {
                    System.out.println("Invalid part data");
                    continue;
                }
                Part part = this.modelMapper.map(partSeedDto, Part.class);
                part.setSupplier(getRandomSupplier());
                this.partRepository.saveAndFlush(part);
            }
        }
    }

    private Supplier getRandomSupplier() {
        long randomId = ThreadLocalRandom.current().nextLong(1, this.supplierRepository.count() + 1);
        return this.supplierRepository.findById(randomId).get();
    }
}
