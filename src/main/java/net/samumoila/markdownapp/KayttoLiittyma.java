package net.samumoila.markdownapp;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;

/**
 * Luokasta luotu olio toimii ohjelman käyttöliittymänä.
 */
class KayttoLiittyma extends BorderPane {
    // Luodaan ylälaidan palkki, johon liitetään eri valikot.
    private MenuBar menuBar = new MenuBar();

    // Yläpalkin ensimmäinen valikko ja sen alta löytyvät valinnat.
    private Menu menuTiedosto = new Menu("Tiedosto");
    protected MenuItem avaaTiedosto = new MenuItem("Avaa tiedosto...");
    protected MenuItem tallennaTiedosto = new MenuItem("Tallenna");
    protected MenuItem suljeSovellus = new MenuItem("Sulje");

    // Vasemmalla näkyvä iso kirjoituskenttä.
    protected TextArea muokkausKentta = new TextArea();
    // Oikealla näkyvä tekstialue. Näyttää "käsitellyn" tekstin.
    private WebView nayttoKentta = new WebView();

    // Tämä teksti näkyy kirjoituskentässä, jos se on tyhjä eikä se ole aktiivisena.
    private String quickStartTeksti = "Kokeile *teksti* kursiiville tai **teksti** tummennetulle tekstille. " +
            "Erilaisia otsikoita saa # tai ##... -merkeillä";
    // Tervetulotoivotus, joka näkyy näyttökentässä oikealla ohjelman käynnistyessä.
    private String tervetuloTeksti = "# Tervetuloa käyttämään Markdown-appia!\n" +
            "\n" +
            "Tällä ohjelmalla on tarkoitus muokata ja katsoa markdown-tiedostoja (*.md).\n" +
            "\n" +
            "Voit käyttää tunnettuja pikanäppäimiä ***ctrl+o*** ja ***ctrl+s***.";

    // Tiedostonsijainnin valitsija
    private FileChooser tiedostonValitsija = new FileChooser();

    // Tiedostonvalitsijalle tiedostoOlio. Tarvitaan oletussijainnin määritykseen File-oliosta.
    private File valittuTiedostoOlio = new File("");

    // Alarivin palkki, jossa näytetään tietoja.
    private HBox alapalkki = new HBox(10);
    private Text merkkiMaaraTeksti = new Text();
    private Text sanaMaaraTeksti = new Text();
    private Text riviMaaraTeksti = new Text();
    private Text alapalkinStatus = new Text();

    // Luodaan valmiiksi markdownparseri ja syötetään sille tervetuloteksti.
    private MarkdownParser markdownParserOlio = new MarkdownParser(tervetuloTeksti);

    /**
     * Asetetaan edellä luodut asiat paikoilleen. Tätä apumetodia kutsutaan varsinaisessa alustajassa
     * ja näin saadaan pidettyä oikea alustaja siistinä.
     */
    private void alustajaApuri() {
        // Koko ikkunan minimikoko ja haluttu koko.
        this.setMinSize(600,600);
        this.setPrefSize(1000, 1000);

        // Liitetään yläosan valikoihin halutut asiat.
        menuTiedosto.getItems().addAll(avaaTiedosto, tallennaTiedosto, suljeSovellus);
        menuBar.getMenus().addAll(menuTiedosto);
        this.setTop(menuBar);

        // Asetetaan muokkauskenttään vihjeteksti.
        muokkausKentta.setPromptText(quickStartTeksti);
        // Ajetaan näyttökentän renderöinti kerran, jotta saadaan tervetuloteksti näkyviin.
        markdownParserOlio.run();
        // Laitetaan nämä kaksi tekstikenttää HBox sisälle, jotta ne ovat tasavertaisia.
        HBox keskiosa = new HBox(muokkausKentta, nayttoKentta);
        this.paivitaKenttienKoko();
        this.setCenter(keskiosa);

        // Alapalkin säädöt
        alapalkki.setPadding(new Insets(5, 5, 5, 5));
        this.paivitaAlapalkki(0, 0, 0);
        alapalkki.getChildren().addAll(merkkiMaaraTeksti, sanaMaaraTeksti, riviMaaraTeksti, alapalkinStatus);
        this.setBottom(alapalkki);

        // Alustetaan tiedostonvalitsijat
        tiedostonValitsija.setTitle("Valitse tiedosto");
        tiedostonValitsija.setTitle("Valitse tallennuspaikka");
        // Mitä tiedostomuotoja tiedostonvalitsija näyttää? Malli otettu javafx:n dokumentaatiosta.
        tiedostonValitsija.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt","*.md"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }

    /**
     * Oletusalustaja ilman aloitustekstiä.
     */
    public KayttoLiittyma() {
        this.alustajaApuri();
        // Ajetaan renderöinti kerran, jotta saadaan tervetuloteksti suoraan näkyviin oikealle puolelle.
        this.naytaTekstiKasiteltyna();
    }

    /**
     * Alustaja, jolle voidaan syöttää aloitusteksti.
     *
     * @param tekstiIn
     */
    public KayttoLiittyma(String tekstiIn) {
        this.alustajaApuri();
        muokkausKentta.setText(tekstiIn);
        // Ajetaan renderöinti kerran, jotta saadaan haluttu aloitusteksti suoraan näkyviin oikealle puolelle.
        markdownParserOlio.run();
    }

    /**
     * Asettaa annetun String-olion tekstin muokkauskentän tekstiksi ja päivittää oikean puolen näkymän.
     *
     * @param tekstiIn
     */
    public void setTeksti(String tekstiIn) {
        muokkausKentta.setText(tekstiIn);
        this.naytaTekstiKasiteltyna();
    }

    /**
     * Palauttaa muokkauskentän tekstin String-oliona.
     *
     * @return
     */
    public String getTeksti() {
        return muokkausKentta.getText();
    }

    /**
     * Kysyy käyttäjältä, minkä tiedoston tämä haluaa avata. Käytetään JavaFX:n FileChooser-toimintoa.
     *
     * @param primaryStage
     * @return
     */
    public String kysyAvausSijainti(Stage primaryStage) {
        String tiedostoSijainti = "";

        // Asetetaan oletuskansio, jos meillä on jo jokin tiedostosijainti tiedossa.
        if (valittuTiedostoOlio.length() > 0) {
            try {
                this.tiedostonValitsija.setInitialDirectory(new File(valittuTiedostoOlio.getCanonicalFile().getParent()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Tässä tapahtuu käyttäjältä tiedustelu. Talletetaan valinta väliaikaiseen olioon.
        File valinta = this.tiedostonValitsija.showOpenDialog(primaryStage);

        // Tarkistetaan tekikö käyttäjä oikeasti valinnan vai poistuiko valintaikkunasta.
        // Jos valintaikkunasta poistutaan perumalla, "valinta" on null. Tätä emme halua kopioida tietona.
        if (valinta != null) {
            valittuTiedostoOlio = valinta;

            // Selvitetään käyttäjän valitseman tiedoston todellinen tiedostopolku.
            // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
            try {
                tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        String tiedostoSijainti = "";

        if (valittuTiedostoOlio.length() > 0) {
            // Asetetaan oletuskansio ja oletustiedosto, jos meillä on jo jokin tiedostosijainti tiedossa.
            try {
                this.tiedostonValitsija.setInitialFileName(valittuTiedostoOlio.getCanonicalPath());
                this.tiedostonValitsija.setInitialDirectory(new File(valittuTiedostoOlio.getCanonicalFile().getParent()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Tässä tapahtuu käyttäjältä tiedustelu. Talletetaan valinta väliaikaiseen olioon.
        File valinta = this.tiedostonValitsija.showSaveDialog(primaryStage);

        // Tarkistetaan tekikö käyttäjä oikeasti valinnan vai poistuiko valintaikkunasta.
        // Jos valintaikkunasta poistutaan perumalla, "valinta" on null. Tätä emme halua kopioida tietona.
        if (valinta != null) {
            valittuTiedostoOlio = valinta;

            // Selvitetään käyttäjän valitseman tiedoston todellinen tiedostopolku.
            // Tämän täytyy olla try-catch, koska eri käyttöjärjestelmät sekoilee.
            try {
                tiedostoSijainti = valittuTiedostoOlio.getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tiedostoSijainti;
    }

    /**
     * Päivittää muokkaus- ja näyttökenttien leveyden vastaamaan puolta
     * koko ikkunan leveydestä. Tämä aktivoidaan aina, kun ikkunan kokoa muutetaan.
     */
    public void paivitaKenttienKoko() {
        muokkausKentta.setPrefWidth(this.getWidth()/2);
        nayttoKentta.setPrefWidth(this.getWidth()/2);
    }

    /**
     * Syöttää muokkauskentän tekstin markdown-parserille, luo uuden säikeen ja ajaa parserin siinä.
     * Lopuksi syöttää parserin tuottaman html-stringin näyttökenttään.
     */
    public void naytaTekstiKasiteltyna() {
        // Ei tehdä mitään, jos edellinen renderöinti on vielä kesken.
        if (!markdownParserOlio.getRenderKesken()) {
            markdownParserOlio.setText(this.getTeksti());
            // Asetetaan renderöinti pyörimään eri säikeeseen, jotta kuormitus tasoittuu.
            // Syntaksi kopioitu https://stackoverflow.com/a/5853198

            Thread saie = new Thread(() -> markdownParserOlio.run());
            saie.start();

            // Odotetaan, että säie suorittaa renderöinnin.
            try {
                saie.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Syötetään markdown-parserin tuottama HTML-koodi suoraan WebView näkymään.
            this.nayttoKentta.getEngine().loadContent(markdownParserOlio.getHtml());
        }
    }

    /**
     * Päivittää alapalkissa näkyvät laskurit annetuilla lukumäärillä.
     *
     * @param charMaara
     * @param sanaMaara
     * @param riviMaara
     */
    public void paivitaAlapalkki(int charMaara, int sanaMaara, int riviMaara) {
        this.merkkiMaaraTeksti.setText("Merkkejä: " + charMaara);
        this.sanaMaaraTeksti.setText("Sanoja: " + sanaMaara);
        this.riviMaaraTeksti.setText("Rivejä: " + riviMaara);
    }

    /**
     * Päivittää alapalkin status-kenttään annetun tekstin.
     *
     * @param statusTeksti
     */
    public void setAlapalkinStatus(String statusTeksti) {
        this.alapalkinStatus.setText(statusTeksti);
    }
}
