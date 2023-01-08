package org.but.shop.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.shop.api.PersonAuthView;
import org.but.shop.data.ShopRepository;
import org.but.shop.exceptions.ResourceNotFoundException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


public class AuthService {          //riesi autentizaciu

    private ShopRepository shopRepository;

    public AuthService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    private PersonAuthView findPersonByEmail(String email) {
        return shopRepository.findPersonByEmail(email);
    }

    public boolean authenticate(String username, String password) {

       /* Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(4, 1024 * 1024, 8, password); // encode
        System.out.println("hashed password is: " + hash);
        */
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        PersonAuthView personAuthView = findPersonByEmail(username);
        if (personAuthView == null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), personAuthView.getPassword()); //porovnanie hashu
        return result.verified;

    }


}
