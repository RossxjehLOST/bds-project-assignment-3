package org.but.shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.but.shop.exceptions.ExceptionHandler;

public class App extends Application {

    private FXMLLoader loader;      //nacitava fxml subory
    private VBox mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            loader = new FXMLLoader(getClass().getResource("Login.fxml"));    // loaduje zo slozky  login.fxml
            mainStage = loader.load();

            primaryStage.setTitle("Login screen");       //nazov
            Scene scene = new Scene(mainStage);
            setUserAgentStylesheet(STYLESHEET_MODENA);

            primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("logos/computer.jpg")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);   //ked sa nieco pokazi tak to zachytava
        }
    }

}