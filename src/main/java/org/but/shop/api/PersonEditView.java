package org.but.shop.api;

public class PersonEditView {

    private Long id;
    private String email;
    private String name;
    private String surname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String givenName) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "PersonEditView{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surename='" + surname + '\'' +
                '}';
    }
}
