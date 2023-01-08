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
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import org.but.shop.api.PersonCreateView;
import org.but.shop.data.ShopRepository;
import org.but.shop.services.ShopService;
import org.but.shop.App;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PersonCreateController {

    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);

    @FXML
    public Button newPersonCreatePerson;

    @FXML
    public Label topgImage;

    @FXML
    private TextField newName;

    @FXML
    private TextField newSurname;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newPhoneNumber;

  /* @FXML
   private TextField newPassword;
*/
    private ShopService shopService;
    private ShopRepository shopRepository;
    private ValidationSupport validation;


    @FXML
    public void initialize() {
        shopRepository = new ShopRepository();
        shopService = new ShopService(shopRepository);

       /* validation = new ValidationSupport();
        validation.registerValidator(newName, Validator.createEmptyValidator("This field can't be empty"));
        validation.registerValidator(newSurname, Validator.createEmptyValidator("This field can't be empty"));
        validation.registerValidator(newEmail, Validator.createEmptyValidator("This field can't be empty"));
        validation.registerValidator(newPhoneNumber, Validator.createEmptyValidator("This field can't be empty"));
      //  validation.registerValidator(newPassword, Validator.createEmptyValidator("This field can't be empty"));

        newPersonCreatePerson.disableProperty().bind(validation.invalidProperty());
*/
        initializeLogos();
        logger.info("PersonCreateController initialized");
    }

    private void initializeLogos() {
        Image tateImage = new Image(App.class.getResourceAsStream("logos/topg.jpg"));
        ImageView andrewImage = new ImageView(tateImage);
        andrewImage.setFitHeight(170);
        andrewImage.setFitWidth(100);
        andrewImage.setPreserveRatio(true);
        topgImage.setGraphic(andrewImage);
    }
    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        PersonCreateView personCreateView = new PersonCreateView();
        personCreateView.setName(newName.getText());
        personCreateView.setSurname(newSurname.getText());
        personCreateView.setEmail(newEmail.getText());
        personCreateView.setPhoneNumber(Integer.valueOf(newPhoneNumber.getText()));


        shopService.createPerson(personCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Person Created Confirmation");
        alert.setHeaderText("Your person was successfully created.");

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
