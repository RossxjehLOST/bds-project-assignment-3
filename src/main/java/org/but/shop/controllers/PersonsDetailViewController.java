package org.but.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.shop.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TextField idTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipCodeTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField houseNumberTextField;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        idTextField.setEditable(false);
        emailTextField.setEditable(false);
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        phoneNumberTextField.setEditable(false);
        cityTextField.setEditable(false);
        zipCodeTextField.setEditable(false);
        streetTextField.setEditable(false);
        houseNumberTextField.setEditable(false);

        loadPersonsData();

        logger.info("PersonsDetailViewController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personBasicView.getId()));
            emailTextField.setText(personBasicView.getEmail());
            nameTextField.setText(personBasicView.getName());
            surnameTextField.setText(personBasicView.getSurname());
            phoneNumberTextField.setText(personBasicView.getPhoneNumber());
            cityTextField.setText(personBasicView.getCity());
            zipCodeTextField.setText(personBasicView.getZipCode());
            streetTextField.setText(personBasicView.getStreet());
            houseNumberTextField.setText(personBasicView.getHouseNumber());
        }
    }

}
