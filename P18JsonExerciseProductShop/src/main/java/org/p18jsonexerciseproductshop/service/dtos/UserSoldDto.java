package org.p18jsonexerciseproductshop.service.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class UserSoldDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    private ProductSoldByUserDto soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductSoldByUserDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductSoldByUserDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
