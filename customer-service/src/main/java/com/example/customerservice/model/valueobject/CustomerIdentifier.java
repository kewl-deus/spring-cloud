package com.example.customerservice.model.valueobject;

import org.springframework.util.Assert;

import java.util.Objects;

public class Id {

    private final String key;

    /** name of identity provider */
    private final String issuer;

    private Id(String key, String issuer) {
        this.key = key;
        this.issuer = issuer;
    }

    @Override
    public String toString() {
        return "Id{" +
                "key='" + key + '\'' +
                ", issuer='" + issuer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return key.equals(id.key) &&
                issuer.equals(id.issuer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, issuer);
    }

    public String getKey() {
        return key;
    }

    public String getIssuer() {
        return issuer;
    }
}
