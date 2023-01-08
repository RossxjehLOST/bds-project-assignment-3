package org.but.shop.api;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InjectionView {
    private final LongProperty id = new SimpleLongProperty();
    private final LongProperty age = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();


    public void setId(long id) {this.id.set(id);}
    public void setAge(long age) {this.age.set(age);}
    public void setName(String name) {this.name.set(name);}
    public void setSurname(String surname) {this.surname.set(surname);}


    public long getId() {return idProperty().get();}
    public long getAge() {
        return ageProperty().get();
    }
    public String getName() {
        return nameProperty().get();
    }
    public String getSurname() {
        return surnameProperty().get();
    }


    public LongProperty idProperty() {
        return id;
    }
    public StringProperty surnameProperty() {
        return surname;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public LongProperty ageProperty() {
        return age;
    }
}
