package com.kenny.api.composite.car;

public class CustomerSummary {
    private int customerId;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String emailAddress;
    private boolean lease;
    private boolean finance;
    private String content;

    public CustomerSummary(int customerId, String firstName, String lastName, int age, String phoneNumber, String emailAddress, boolean lease, boolean finance, String content) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.lease = lease;
        this.finance = finance;
        this.content = content;
    }
    public CustomerSummary() {
        this.customerId = 0;
        this.firstName = null;
        this.lastName = null;
        this.age = 0;
        this.phoneNumber = null;
        this.emailAddress = null;
        this.lease = false;
        this.finance = false;
        this.content = null;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
