package org.but.shop.api;

public class PersonAuthView {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   @Override
    public String toString() {
        return "PersonAuthView{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
