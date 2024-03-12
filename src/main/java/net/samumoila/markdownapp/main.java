package net.samumoila.markdownapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class main extends Application{
    static TextSimple markdownteksti = new TextSimple(MyFileReader.readFile("README.md"));
//    static TextSimple markdownteksti = new TextSimple();
    UserInterface UI = new UserInterface();

    public static void main(String[] args) {
        System.out.println(markdownteksti);
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        UI.setText(markdownteksti.getText());

        UI.closeApp.setOnAction(e -> {
            System.exit(0);
        });

        Scene kehys = new Scene(UI, 600, 500);
        primaryStage.setScene(kehys);
        primaryStage.setTitle("Markdown-app");
        primaryStage.show();
    }
}
