package com.example.customerservice.model.valueobject;

import org.springframework.util.Assert;

import java.util.Objects;

public class ContractNumber {
    private final String value;

    public static ContractNumber from(String value){
        Assert.hasText(value, "Contract number cannot be null or empty");
        return new ContractNumber(value);
    }

    private ContractNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ContractNumber{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractNumber that = (ContractNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
