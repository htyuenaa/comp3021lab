package base;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloWorld extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        stage.setTitle("HELLO WORLD!");
        VBox vbox = new VBox(); //create pane
        vbox.setPadding(new Insets(10)); //add space
        vbox.setSpacing(8); //vertical space between nodes
        // add two nodes
        vbox.getChildren().add(new Button("This is a button"));
        vbox.getChildren().add(new Text("Hello World!"));
        //create scene
        Scene scene = new Scene(vbox, 200, 100);
        stage.setScene(scene);
        stage.show();
    }
}
