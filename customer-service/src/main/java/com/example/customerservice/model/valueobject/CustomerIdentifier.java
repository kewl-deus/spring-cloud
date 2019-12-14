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
@AllArgsConstructor
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
}
