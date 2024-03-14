package net.samumoila.markdownapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class sandbox extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Text text1 = new Text("Big italic red text");
        text1.setFill(Color.RED);
        text1.setFont(Font.font("Helvetica", FontPosture.ITALIC, 40));
        Text text2 = new Text(" little bold blue text");
        text2.setFill(Color.BLUE);
        text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
        TextFlow textFlow = new TextFlow(text1, text2);

        BorderPane toottipaneeli = new BorderPane();
        toottipaneeli.setCenter(textFlow);

        Scene kehys = new Scene(toottipaneeli, 600, 500);
        primaryStage.setScene(kehys);
        primaryStage.setTitle("Markdown-app");
        primaryStage.show();
    }
}
