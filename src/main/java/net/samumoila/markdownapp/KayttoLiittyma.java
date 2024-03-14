package net.samumoila.markdownapp;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

class KayttoLiittyma extends BorderPane {
    // Luodaan ylälaidan palkki, johon liitetään eri valikot.
    MenuBar menuBar = new MenuBar();

    // Yläpalkin ensimmäinen valikko ja sen alta löytyvät valinnat.
    Menu menuTiedosto = new Menu("Tiedosto");
    protected MenuItem avaaTiedosto = new MenuItem("Avaa tiedosto...");
    protected MenuItem tallennaTiedosto = new MenuItem("Tallenna");
    protected MenuItem suljeSovellus = new MenuItem("Sulje");

    // Keskellä näkyvä iso kirjoituskenttä.
    TextArea muokkausKentta = new TextArea();
    // Tämä teksti näkyy, jos kirjoituskenttä on tyhjä.
    String quickStartTeksti = "Try *text* for cursive, or **text** for bold. Get different headings with # or ##.";

    // Tiedostonsijainnin valitsija
    FileChooser tiedostonValitsija = new FileChooser();
    FileChooser tallennuksenValitsija = new FileChooser();
    // Tiedostonvalitsijoille tiedostoOlio. Tarvitaan oletussijainnin määritykseen File-oliosta.
    File valittuTiedostoOlio = new File("");

    /**
     * Asetetaan edellä luodut asiat paikoilleen. Tätä apumetodia kutsutaan varsinaisessa alustajassa
     * ja näin saadaan pidettyä oikea alustaja siistinä.
     */
    private void alustajaApuri() {
        menuTiedosto.getItems().addAll(avaaTiedosto, tallennaTiedosto, suljeSovellus);
        menuBar.getMenus().addAll(menuTiedosto);
        muokkausKentta.setPromptText(quickStartTeksti);
        this.setCenter(muokkausKentta);
        this.setTop(menuBar);
        tiedostonValitsija.setTitle("Valitse tiedosto");
        tallennuksenValitsija.setTitle("Valitse tallennuspaikka");
        // Mitä tiedostomuotoja tiedostonvalitsija näyttää?
        tiedostonValitsija.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt","*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    public KayttoLiittyma() {
        this.alustajaApuri();
        muokkausKentta.setText("");
        }

    public KayttoLiittyma(String text) {
        this.alustajaApuri();
        muokkausKentta.setText(text);
    }

    public void setTeksti(String text) {
        muokkausKentta.setText(text);
    }

    public String getTeksti() {
        return muokkausKentta.getText();
    }

    public String kysyAvausSijainti(Stage primaryStage) {
        String tiedostoSijainti;
        if (valittuTiedostoOlio.exists()) {
            // Asetetaan oletuskansio, jos meillä on jo jokin tiedostosijainti tiedossa.
            try {
                this.tiedostonValitsija.setInitialDirectory(new File(valittuTiedostoOlio.getCanonicalFile().getParent()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        valittuTiedostoOlio = this.tiedostonValitsija.showOpenDialog(primaryStage);
        try { // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
            tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tiedostoSijainti;
    }
    public String kysyTallennusSijainti(Stage primaryStage) {
        String tiedostoSijainti;
        if (valittuTiedostoOlio.exists()) {
            // Asetetaan oletuskansio, jos meillä on jo jokin tiedostosijainti tiedossa.
            try {
                this.tallennuksenValitsija.setInitialFileName(valittuTiedostoOlio.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        valittuTiedostoOlio = this.tiedostonValitsija.showSaveDialog(primaryStage);
        try { // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
            tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tiedostoSijainti;
    }
}
