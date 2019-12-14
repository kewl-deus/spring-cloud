package com.example.customerservice.model.aggregate;

import com.example.customerservice.model.valueobject.Address;
import com.example.customerservice.model.valueobject.Name;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @Column(nullable = true)
    private LocalDate birthDay;

    @Embedded
    @Column(nullable = true)
    @NonNull
    private Address address;

    public Customer(Name firstname, Name lastname) {
        this.firstname = firstname.getValue();
        this.lastname = lastname.getValue();
    }
}