package org.p18jsonexerciseproductshop.service.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserSoldProductsDto implements Serializable {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<ProductSoldDto> soldProductsList;

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

    public List<ProductSoldDto> getSoldProductsList() {
        return soldProductsList;
    }

    public void setSoldProductsList(List<ProductSoldDto> soldProductsList) {
        this.soldProductsList = soldProductsList;
    }
}
