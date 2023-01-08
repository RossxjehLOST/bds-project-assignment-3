package org.but.shop.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.but.shop.api.PersonBasicView;
import org.but.shop.api.PersonDetailView;
import org.but.shop.api.PersonEditView;
import org.but.shop.data.ShopRepository;
import org.but.shop.services.ShopService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class PersonsEditController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsEditController.class);

    @FXML
    public Button editPersonButton;
    @FXML
    public TextField idTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;


    private ShopService shopService;
    private ShopRepository shopRepository;
    private ValidationSupport validation;

    // used to reference the stage and to get passed data through it
    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        shopRepository = new ShopRepository();
        shopService = new ShopService(shopRepository);

        validation = new ValidationSupport();
        validation.registerValidator(idTextField, Validator.createEmptyValidator("The id must not be empty."));
        idTextField.setEditable(false);
        validation.registerValidator(emailTextField, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(nameTextField, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(surnameTextField, Validator.createEmptyValidator("The last name must not be empty."));

        editPersonButton.disableProperty().bind(validation.invalidProperty());

        loadPersonsData();

        logger.info("PersonsEditController initialized");
    }


    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personDetailView = (PersonDetailView) stage.getUserData();
            idTextField.setText(String.valueOf(personDetailView.getId()));
            emailTextField.setText(personDetailView.getEmail());
            nameTextField.setText(personDetailView.getName());
            surnameTextField.setText(personDetailView.getSurname());

        }
    }

    @FXML
    public void handleEditPersonButton(ActionEvent event) {
        /* can be written easier, its just for better explanation here on so many lines
        Long id = Long.valueOf(idTextField.getText());
        String email = emailTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
*/

        PersonEditView personEditView = new PersonEditView();
        personEditView.setId(Long.valueOf(idTextField.getText()));
        personEditView.setEmail(emailTextField.getText());
        personEditView.setName(nameTextField.getText());
        personEditView.setSurname(surnameTextField.getText());

        shopService.editPerson(personEditView);

        personEditedConfirmationDialog();
    }

    private void personEditedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Edited Confirmation");
        alert.setHeaderText("Your person was successfully edited.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
