package com.example.customerservice.model.valueobject;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ZipCode {

    private final String value;

    public static ZipCode of(String value){
        return new ZipCode(value);
    }

    private ZipCode(String value) {
        Assert.hasLength(value, "ZipCode cannot be empty");
        if (value.length() != 5) throw new IllegalArgumentException("ZipCode must have a length of 5");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ZipCode{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZipCode zipCode = (ZipCode) o;
        return Objects.equals(value, zipCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
