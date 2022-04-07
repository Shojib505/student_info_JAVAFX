package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	// write your code here
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent parent = FXMLLoader.load(getClass().getResource("main_windwos.fxml"));

        Scene scene = new Scene(parent);
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
