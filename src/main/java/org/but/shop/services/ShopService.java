package org.but.shop.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.shop.api.PersonBasicView;
import org.but.shop.api.PersonCreateView;
import org.but.shop.api.PersonDetailView;
import org.but.shop.api.PersonEditView;
import org.but.shop.api.PersonFilterView;
import org.but.shop.api.InjectionView;
import org.but.shop.data.ShopRepository;

import java.util.List;

/**
 * Class representing business logic on top of the Persons
 */
public class ShopService {

    private ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public PersonDetailView getPersonDetailView(Long id) {
        return shopRepository.findPersonDetailedView(id);
    }

    public List<PersonBasicView> getPersonsBasicView() {
        return shopRepository.getPersonsBasicView();
    }

    public List<PersonFilterView> getPersonFilterView(String text){
        return shopRepository.getPersonFilterView(text);
    }

    public List<InjectionView> getInjectionView(String input){
        return shopRepository.getInjectionView(input);
    }

    public void createPerson(PersonCreateView personCreateView) {
        // the following three lines can be written in one code line (only for more clear explanation it is written in three lines
       // char[] originalPassword = personCreateView.getPwd();
       // char[] hashedPassword = hashPassword(originalPassword);
       // personCreateView.setPwd(hashedPassword);

        shopRepository.createPerson(personCreateView);
    }

    public void editPerson(PersonEditView personEditView) {
        shopRepository.editPerson(personEditView);
    }


    /**
     * <p>
     * Note: For implementation details see: https://github.com/patrickfav/bcrypt
     * </p>
     *
     * @param password to be hashed
     * @return hashed password
     */
    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }

}
