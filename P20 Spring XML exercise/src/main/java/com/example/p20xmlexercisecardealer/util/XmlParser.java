package com.example.p20xmlexercisecardealer.util;

import jakarta.xml.bind.JAXBException;

public interface XmlParser {
    <E> E parse(Class<E> clazz, String path) throws JAXBException;

    <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException;
    <E> void exportToFile(E object, String path) throws JAXBException;
}
