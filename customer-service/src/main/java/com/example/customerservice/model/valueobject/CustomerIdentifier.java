package com.example.customerservice.model.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@EqualsAndHashCode
@Embeddable
public class CustomerIdentifier {

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String type;

    public static CustomerIdentifier from(String key, String type) {
        return new CustomerIdentifier(key, type);
    }

    public static CustomerIdentifier from(String key, IdentitfierType type) {
        return new CustomerIdentifier(key, type.name());
    }

    public boolean isOf(IdentitfierType type){
        return type.name().equals(this.getType());
    }
}
