package com.example.customerservice.model.valueobject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class CustomerIdentifier {

    private final String key;

    private final String type;

    public static CustomerIdentifier from(String key, String type) {
        return new CustomerIdentifier(key, type);
    }

    private CustomerIdentifier(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "CustomerIdentifier{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerIdentifier customerIdentifier = (CustomerIdentifier) o;
        return key.equals(customerIdentifier.key) &&
                type.equals(customerIdentifier.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type);
    }
}
