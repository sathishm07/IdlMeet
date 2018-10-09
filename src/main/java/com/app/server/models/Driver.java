package com.app.server.models;

public class Driver {
    String id=null;
    String firstName;
    String middleName;
    String lastName;
    String address1;
    String address2;
    String city;
    String state;
    String country;
    String postalCode;

    public Driver(String firstName, String middleName, String lastName, String address1, String address2, String city, String state, String country,String postalCode) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
    public void setId(String id) {
        this.id = id;
    }
}
