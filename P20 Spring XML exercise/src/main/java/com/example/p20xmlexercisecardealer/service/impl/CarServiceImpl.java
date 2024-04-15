package com.example.p20xmlexercisecardealer.service.impl;

import com.example.p20xmlexercisecardealer.data.entities.Car;
import com.example.p20xmlexercisecardealer.data.entities.Part;
import com.example.p20xmlexercisecardealer.data.repositories.CarRepository;
import com.example.p20xmlexercisecardealer.data.repositories.PartRepository;
import com.example.p20xmlexercisecardealer.service.CarService;
import com.example.p20xmlexercisecardealer.service.dtos.exports.*;
import com.example.p20xmlexercisecardealer.service.dtos.imports.CarSeedDto;
import com.example.p20xmlexercisecardealer.service.dtos.imports.CarSeedRootDto;
import com.example.p20xmlexercisecardealer.util.ValidationUtil;
import com.example.p20xmlexercisecardealer.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String FILE_PATH = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\imports\\cars.xml";
    private static final String FILE_PATH_EXPORT_TOYOTA_CARS = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\exports\\toyota-cars.xml";
    private static final String FILE_PATH_CARS_AND_PARTS_XML = "C:\\Rado\\Java Rado\\Java Spring Data\\P18 JSON Processing Exercise\\P20XmlExerciseCarDealer\\src\\main\\resources\\xml\\exports\\cars-and-parts.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ValidationUtil validationUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCars() throws JAXBException {
        if (this.carRepository.count() == 0) {
            CarSeedRootDto carSeedRootDto = xmlParser.parse(CarSeedRootDto.class, FILE_PATH);
            for (CarSeedDto carSeedDto : carSeedRootDto.getCarSeedDtoList()) {
                if (!this.validationUtil.isValid(carSeedDto)) {
                    System.out.println("Invalid car data");
                    continue;
                }
                Car car = this.modelMapper.map(carSeedDto, Car.class);
                car.setParts(getRandomPartsList());
                this.carRepository.saveAndFlush(car);
            }
        }
    }

    @Override
    public void exportCarsByGivenBrand(String brand) throws JAXBException {
        List<CarToyotaDto> carToyotaDtoList = this.carRepository.findAllByMakeOrderByModelAndTravelledDistanceDesc(brand)
                .stream()
                .map(c -> this.modelMapper.map(c, CarToyotaDto.class))
                .collect(Collectors.toList());
        CarToyotaRootDto carToyotaRootDto = new CarToyotaRootDto();
        carToyotaRootDto.setCarToyotaDtoList(carToyotaDtoList);

        this.xmlParser.exportToFile(CarToyotaRootDto.class, carToyotaRootDto, FILE_PATH_EXPORT_TOYOTA_CARS);
    }

    @Override
    public void exportCarsWithTheirParts() throws JAXBException {
        List<CarCollectionDto> carCollectionDtoList = this.carRepository.findAllBy()
                .stream()
                .map(c -> {
                    CarCollectionDto carCollectionDto = this.modelMapper.map(c, CarCollectionDto.class);
                    List<PartDto> partDtoList = c.getParts().stream()
                            .map(p -> modelMapper.map(p, PartDto.class))
                            .collect(Collectors.toList());
                    PartRootDto partRootDto = new PartRootDto();
                    partRootDto.setPartDtoList(partDtoList);
                    carCollectionDto.setPartRootDtoList(partRootDto);
                    return carCollectionDto;
                })
                .collect(Collectors.toList());

        CarCollectionRootDto carCollectionRootDto = new CarCollectionRootDto();
        carCollectionRootDto.setCarCollectionDtoList(carCollectionDtoList);
        this.xmlParser.exportToFile(CarCollectionRootDto.class, carCollectionRootDto, FILE_PATH_CARS_AND_PARTS_XML);
    }

    private Set<Part> getRandomPartsList() {
        Set<Part> partsSet = new HashSet<>();
        int setSize = ThreadLocalRandom.current().nextInt(3, 6);
        for (int i = 0; i < setSize; i++) {
            long partId = ThreadLocalRandom.current().nextLong(1, this.partRepository.count() + 1);
            partsSet.add(this.partRepository.findById(partId).get());
        }
        return partsSet;
    }
}
