package org.p18jsonexerciseproductshop.service.dtos;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public class CategorySeedDto implements Serializable {
    @Expose
    @NotNull
    @Length(min = 3, max = 15)
    private String name;

    public CategorySeedDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
