package com.example.customerservice.model.valueobject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Id {

    private final Long value;

    public static Id from(long value){
        return new Id(value);
    }

    public Id(long value) {
        if (value < 1) throw new IllegalArgumentException("Id cannot be smaller than 1");
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Id{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return value == id.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
