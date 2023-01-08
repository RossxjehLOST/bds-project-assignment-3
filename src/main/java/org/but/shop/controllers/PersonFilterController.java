package org.but.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.shop.api.PersonFilterView;
import org.but.shop.data.ShopRepository;
import org.but.shop.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonFilterController {
    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    @FXML
    public TableColumn<PersonFilterView, Long> personsId;
    @FXML
    public TableColumn<PersonFilterView, String> name;
    @FXML
    public TableColumn<PersonFilterView, String> surname;
    @FXML
    public TableColumn<PersonFilterView, String> email;
    @FXML
    public TableView<PersonFilterView> systemPersonsTableView;

    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private ShopService shopService;
    private ShopRepository shopRepository;
    @FXML
    private void initialize(){
        shopRepository = new ShopRepository();
        shopService = new ShopService(shopRepository);

        personsId.setCellValueFactory(new PropertyValueFactory<PersonFilterView, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<PersonFilterView, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<PersonFilterView, String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<PersonFilterView, String>("email"));


        ObservableList<PersonFilterView> observablePersonList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonList);
        logger.info("PersonFilterController initialized");

    }
    private ObservableList<PersonFilterView> initializePersonsData() {

        String text = (String) stage.getUserData();
        System.out.println("controller "+text);
        List<PersonFilterView> persons = shopService.getPersonFilterView(text);
        return FXCollections.observableArrayList(persons);
    }


}

