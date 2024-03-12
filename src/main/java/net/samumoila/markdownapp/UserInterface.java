package net.samumoila.markdownapp;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

class UserInterface extends BorderPane {
    protected MenuItem openFile = new MenuItem("Avaa tiedosto...");
    protected MenuItem closeApp = new MenuItem("Sulje");
    Menu menuFile = new Menu("File");
    MenuBar menuBar = new MenuBar();
    TextArea muokkausKentta = new TextArea();
    String quickStartText = "Try *text* for cursive, or **text** for bold. Get different headings with # or ##.";

    private void constructorHelper() {
        menuFile.getItems().addAll(openFile, closeApp);
        menuBar.getMenus().addAll(menuFile);
        muokkausKentta.setPromptText(quickStartText);
        this.setCenter(muokkausKentta);
        this.setTop(menuBar);
    }

    public UserInterface() {
        this.constructorHelper();
        muokkausKentta.setText("");
        }

    public UserInterface(String text) {
        this.constructorHelper();
        muokkausKentta.setText(text);
    }

    public void setText(String text) {
        muokkausKentta.setText(text);
    }
}
