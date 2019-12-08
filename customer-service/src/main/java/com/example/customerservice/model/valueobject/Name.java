package com.example.customerservice.model.valueobject;

import org.springframework.util.Assert;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private final String value;

    public static Name of(String value){
        return new Name(value);
    }

    private Name(String value)
    {
        Assert.hasText(value, "Name cannot be empty");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Name{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
