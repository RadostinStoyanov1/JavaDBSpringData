package p12springdataintrobookshop.data.repositories;

import p12springdataintrobookshop.data.entities.enums.AgeRestriction;
import p12springdataintrobookshop.data.entities.enums.EditionType;

import java.math.BigDecimal;

public interface BookInfo {
    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
