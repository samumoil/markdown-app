package net.samumoila.markdownapp;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
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
    // Oikealla näkyvä tekstialue. Näyttää "käsitellyn" tekstin.
    TextFlow nayttoKentta = new TextFlow();
    // Muuttuja muokkauskentän ja näyttökentän minimikoon säätämiseen.
    private double tekstialueidenKoko = 200;

    // Tiedostonsijainnin valitsijat
    FileChooser tiedostonValitsija = new FileChooser();
    FileChooser tallennuksenValitsija = new FileChooser();
    // Tiedostonvalitsijoille tiedostoOlio. Tarvitaan oletussijainnin määritykseen File-oliosta.
    File valittuTiedostoOlio = new File("");

    /**
     * Asetetaan edellä luodut asiat paikoilleen. Tätä apumetodia kutsutaan varsinaisessa alustajassa
     * ja näin saadaan pidettyä oikea alustaja siistinä.
     */
    private void alustajaApuri() {
        // Koko ikkunan minimikoko.
        this.setMinSize(600,600);

        // Liitetään yläosan valikoihin halutut asiat.
        menuTiedosto.getItems().addAll(avaaTiedosto, tallennaTiedosto, suljeSovellus);
        menuBar.getMenus().addAll(menuTiedosto);
        this.setTop(menuBar);

        // Säädetään keskiosan tekstikentät.
        muokkausKentta.setPromptText(quickStartTeksti);
        muokkausKentta.setMinSize(tekstialueidenKoko, tekstialueidenKoko);
        nayttoKentta.setMinSize(tekstialueidenKoko, tekstialueidenKoko);
        // Laitetaan kaksi tekstikenttää HBox sisälle, jotta ne ovat tasavertaisia.
        HBox keskiosa = new HBox(muokkausKentta, nayttoKentta);
        this.paivitaKenttienKoko();
        this.setCenter(keskiosa);

        // Alustetaan tiedostonvalitsijat
        tiedostonValitsija.setTitle("Valitse tiedosto");
        tallennuksenValitsija.setTitle("Valitse tallennuspaikka");
        // Mitä tiedostomuotoja tiedostonvalitsija näyttää? Malli otettu javafx:n dokumentaatiosta.
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

    /**
     * Kysyy käyttäjältä, minkä tiedoston tämä haluaa avata. Käytetään JavaFX:n FileChooser-toimintoa.
     * @param primaryStage
     * @return
     */
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

        // Tässä tapahtuu käyttäjältä tiedustelu.
        valittuTiedostoOlio = this.tiedostonValitsija.showOpenDialog(primaryStage);

        // Selvitetään käyttäjän valitseman tiedoston todellinen tiedostopolku.
        // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
        try {
            tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tiedostoSijainti;
    }

    /**
     * Kysyy käyttäjältä tallennussijaintia käyttämällä JavaFX:n FileChooser-toimintoa.
     *
     * @param primaryStage
     * @return
     */
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

        // Tässä tapahtuu käyttäjältä tiedustelu.
        valittuTiedostoOlio = this.tiedostonValitsija.showSaveDialog(primaryStage);

        // Selvitetään käyttäjän valitseman tiedoston todellinen tiedostopolku.
        // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
        try {
            tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tiedostoSijainti;
    }

    /**
     * Päivittää muokkaus- ja näyttökenttien leveyden vastaamaan puolta
     * koko ikkunan leveydestä.
     */
    public void paivitaKenttienKoko() {
        muokkausKentta.setPrefWidth(this.getWidth()/2);
        nayttoKentta.setPrefWidth(this.getWidth()/2);
    }
}
