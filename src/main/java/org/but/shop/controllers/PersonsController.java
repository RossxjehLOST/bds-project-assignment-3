package org.but.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.shop.App;
import org.but.shop.api.PersonBasicView;
import org.but.shop.api.PersonDetailView;
import org.but.shop.data.ShopRepository;
import org.but.shop.exceptions.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.but.shop.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PersonsController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    @FXML
    public Button addPersonButton;

    @FXML
    public Button refreshButton;

    @FXML
    public Button filterButton;

    @FXML
    public TextField searchBar;

    @FXML
    public Button injectionButton;

    @FXML
    private TableColumn<PersonBasicView, Long> personsId;
    @FXML
    private TableColumn<PersonBasicView, String> personsCity;
    @FXML
    private TableColumn<PersonBasicView, String> personsEmail;
    @FXML
    private TableColumn<PersonBasicView, String> personsName;
    @FXML
    private TableColumn<PersonBasicView, String> personsSurname;
    @FXML
    private TableColumn<PersonBasicView, Integer> personsPhoneNumber;
    @FXML
    private TableView<PersonBasicView> systemPersonsTableView;
//    @FXML
//    public MenuItem exitMenuItem;

    private ShopService shopService;
    private ShopRepository shopRepository;

    public PersonsController() {
    }

    @FXML
    private void initialize() {
        shopRepository = new ShopRepository();
        shopService = new ShopService(shopRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        personsId.setCellValueFactory(new PropertyValueFactory<PersonBasicView, Long>("id"));
        personsEmail.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("email"));
        personsSurname.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("surname"));
        personsName.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("name"));
        personsPhoneNumber.setCellValueFactory(new PropertyValueFactory<PersonBasicView, Integer>("phoneNumber"));


        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);

        systemPersonsTableView.getSortOrder().add(personsId);


        initializeTableViewSelection();
        loadIcons();

        logger.info("PersonsController initialized");
    }



    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit customer");
        MenuItem detailedView = new MenuItem("Show address details");
        MenuItem delete = new MenuItem("Delete customer");

        edit.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem(); //EDIT PERSON
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();

                Long personId = personView.getId();
                PersonDetailView personDetailView = shopService.getPersonDetailView(personId);

                stage.setUserData(personDetailView);
                stage.setTitle("Edit customer");

                PersonsEditController controller = new PersonsEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);
                stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem(); //DETAILED VIEW
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonsDetailView.fxml"));
                Stage stage = new Stage();

                Long personId = personView.getId();
                PersonDetailView personDetailView = shopService.getPersonDetailView(personId);

                stage.setUserData(personDetailView);
                stage.setTitle("Address details");

                PersonsDetailViewController controller = new PersonsDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);
                stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));


                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        delete.setOnAction ((ActionEvent event) -> {                            //DELETE PERSON
            PersonBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            Long personsId = personView.getId();
            shopRepository.removePerson(personsId);
                });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        menu.getItems().add(delete);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<PersonBasicView> initializePersonsData() {
        List<PersonBasicView> persons = shopService.getPersonsBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddPersonButton(ActionEvent actionEvent) {            //CREATE PERSON
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/PersonsCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("Add customer");
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));


            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }


    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }

    public void handleFilterButton(ActionEvent actionEvent){            //FILTER PERSON
        try {
            String text = searchBar.getText();
            System.out.println("handler" +text);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/CustomerFilter.fxml"));
            Stage stage = new Stage();
            PersonFilterController personFilterController = new PersonFilterController();
            stage.setUserData(text);
            personFilterController.setStage(stage);
            fxmlLoader.setController(personFilterController);
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);


            stage.setTitle("Filtered view");
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));
            stage.show();

        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }

    }

    public void handleInjectionButton(ActionEvent actionEvent){     //INJECTION
        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/Injection.fxml"));
            Stage stage = new Stage();
            InjectionController injectionController = new InjectionController();

            injectionController.setStage(stage);
            fxmlLoader.setController(injectionController);
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);


            stage.setTitle("SQL Injection training");
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));
            stage.show();
        }catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }
}
