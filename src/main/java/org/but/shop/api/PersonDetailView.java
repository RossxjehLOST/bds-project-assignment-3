package org.but.shop.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonDetailView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();

    private StringProperty zipCode = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();

    private StringProperty houseNumber = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(Long id) {
        this.idProperty().setValue(id);
    }

    public String getEmail() {
        return emailProperty().get();
    }

    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String name) {
        this.nameProperty().setValue(name);
    }

    public String getSurname() {
        return surameProperty().get();
    }

    public void setSurname(String surname) {
        this.surameProperty().setValue(surname);
    }

    public String getPhoneNumber() {
        return phoneNumberProperty().get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty().set(phoneNumber);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public String getZipCode() {
        return zipCodeProperty().get();
    }

    public void setZipCode(String zipCode) {
        this.zipCodeProperty().setValue(zipCode);
    }

    public String getStreet() {
        return streetProperty().get();
    }

    public void setStreet(String street) {
        this.streetProperty().setValue(street);
    }

    public String getHouseNumber() {return houseNumberProperty().get();}

    public void setHouseNumber(String houseNumber) {
        this.houseNumberProperty().setValue(houseNumber);
    }


    public LongProperty idProperty() {
        return id;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surameProperty() {
        return surname;
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty zipCodeProperty() {
        return zipCode;
    }

    public StringProperty streetProperty() {
        return street;
    }

    public StringProperty houseNumberProperty() {
        return houseNumber;
    }



}
