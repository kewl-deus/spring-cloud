package com.example.customerservice.model.aggregate;

import com.example.customerservice.model.valueobject.Address;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


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

    @Embedded
    @Column(nullable = true)
    private Address address;

    public Customer() {
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}