package org.but.shop.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonFilterView {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    ////////////////
    public void setId(long id) {
        this.id.set(id);
    }


    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    ////////////////////////////
    public long getId() {
        return idProperty().get();
    }


    public String getName() {
        return nameProperty().get();
    }

    public String getSurname() {
        return surnameProperty().get();
    }
    public String getEmail() {
        return emailProperty().get();
    }

    ///////////////////////

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }
    public StringProperty emailProperty() {
        return email;
    }

    public LongProperty idProperty() {
        return id;
    }

}
