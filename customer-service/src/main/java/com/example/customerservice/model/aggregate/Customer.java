package com.example.customerservice.model.aggregate;

import com.example.customerservice.model.valueobject.Address;
import com.example.customerservice.model.valueobject.Name;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;


/*
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
*/
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;

    private String lastname;

    @Column(nullable = true)
    private LocalDate birthDay;

    @Embedded
    @Column(nullable = true)
    private Address address;

    protected Customer() {
    }

    public Customer(Name firstname, Name lastname){
        this.firstname = firstname.getValue();
        this.lastname = lastname.getValue();
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Address getAddress() {
        return address;
    }
}