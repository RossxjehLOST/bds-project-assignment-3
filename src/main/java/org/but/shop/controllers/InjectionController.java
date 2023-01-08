package org.but.shop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.shop.api.InjectionView;
import org.but.shop.data.ShopRepository;
import org.but.shop.services.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InjectionController {
    private static final Logger logger = LoggerFactory.getLogger(PersonCreateController.class);
    @FXML
    private TableColumn<InjectionView, Long> id;
    @FXML
    private TableColumn<InjectionView, Long> age;
    @FXML
    private TableColumn<InjectionView, String> name;
    @FXML
    private TableColumn<InjectionView, String> surname;

    @FXML
    private TableView<InjectionView> systemPersonsTableView;
    @FXML
    private TextField inputField;
    @FXML
    private Button confirmButton;

    private ShopService shopService;
    private ShopRepository shopRepository;

    public Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        shopRepository = new ShopRepository();
        shopService = new ShopService(shopRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        id.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("id"));
        name.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<InjectionView, String>("surname"));
        age.setCellValueFactory(new PropertyValueFactory<InjectionView, Long>("age"));

        logger.info("InjectionController initialized");

    }
    private ObservableList<InjectionView> initializePersonsData() {

        String input = inputField.getText();
        List<InjectionView> persons = shopService.getInjectionView(input);
        return FXCollections.observableArrayList(persons);
    }
    public void handleConfirmButton(ActionEvent actionEvent){
        ObservableList<InjectionView> observablePersonList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonList);

    }


}