package com.kenny.microservices.core.customer.Dao;

import javax.persistence.*;

@Entity
@Table(name = "customers", indexes = { @Index(name = "customers_unique_idx", unique = true, columnList = "carId,customerId") })
public class CustomerEntity {
    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private int carId;
    private int customerId;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String emailAddress;
    private boolean lease;
    private boolean finance;
    private String description;

    public CustomerEntity(){
        this.carId = 0;
        this.customerId = 0;
        this.firstName = null;
        this.lastName = null;
        this.age = 0;
        this.phoneNumber = null;
        this.emailAddress = null;
        this.lease = false;
        this.finance = false;
        this.description = null;
    }

    public CustomerEntity(int carId, int customerId, String firstName, String lastName, int age, String phoneNumber, String emailAddress, boolean lease, boolean finance, String description) {
        this.carId = carId;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.lease = lease;
        this.finance = finance;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isLease() {
        return lease;
    }

    public void setLease(boolean lease) {
        this.lease = lease;
    }

    public boolean isFinance() {
        return finance;
    }

    public void setFinance(boolean finance) {
        this.finance = finance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
