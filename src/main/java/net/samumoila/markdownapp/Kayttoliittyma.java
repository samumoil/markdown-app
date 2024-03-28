package net.samumoila.markdownapp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.IOException;

/**
 * Luokasta luotu olio toimii ohjelman käyttöliittymänä. Luokassa on myös logiikkaa liittyen tekstin näyttämisestä
 * markdown-muodossa. Luokka luo ja kutsuu MarkdownParser-oliota tekstin muuntamiseksi.
 */
class Kayttoliittyma extends BorderPane {
    // Luodaan ylälaidan palkki, johon liitetään eri valikot.
    private MenuBar menuBar = new MenuBar();

    // Yläpalkin ensimmäinen valikko ja sen alta löytyvät valinnat. Menu-valikot täytyy olla vähintään protected,
    // jotta main.java voi seurata niiden aktivointia eventhandlerilla.
    private Menu menuTiedosto = new Menu("Tiedosto");
    protected MenuItem avaaTiedosto = new MenuItem("Avaa tiedosto...");
    protected MenuItem tallennaTiedosto = new MenuItem("Tallenna");
    protected MenuItem suljeSovellus = new MenuItem("Sulje");

    // Vasemmalla näkyvä iso kirjoituskenttä. Tekstikentän täytyy olla protected, jotta main.java voi seurata sen
    // aktivointia eventhandlerilla.
    protected TextArea muokkausKentta = new TextArea();
    // Oikealla näkyvä tekstialue. Näyttää "käsitellyn" tekstin.
    private WebView nayttoKentta = new WebView();
    // Luodaan kahdelle edelliselle HBox, jotta ne ovat tasavertaisia. Alustajassa liitetään tähän.
    HBox keskiosa = new HBox(muokkausKentta, nayttoKentta);

    // Tämä liittyy "piilota/näytä markdown" nappiin.
    private boolean onkoMarkdownNakyvissa = true;

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
    private HBox alapalkinMittarit = new HBox(10);
    private Text merkkiMaaraTeksti = new Text();
    private Text sanaMaaraTeksti = new Text();
    private Text riviMaaraTeksti = new Text();
    private Text alapalkinStatus = new Text();
    protected Button markdownNappi = new Button("Piilota Markdown");
    private BorderPane alapalkki = new BorderPane();

    // Luodaan valmiiksi markdownparseri.
    private MarkdownParser markdownParserOlio = new MarkdownParser();

    /**
     * Asetetaan edellä luodut asiat paikoilleen. Tätä apumetodia kutsutaan varsinaisissa alustajissa
     * ja näin saadaan pidettyä oikeat alustajat siistinä.
     */
    private void alustajaApuri() {
        // Koko ikkunan minimikoko ja haluttu koko.
        this.setPrefSize(800, 800);

        // Liitetään yläosan valikoihin halutut asiat.
        menuTiedosto.getItems().addAll(avaaTiedosto, tallennaTiedosto, suljeSovellus);
        menuBar.getMenus().addAll(menuTiedosto);
        this.setTop(menuBar);

        // Asetetaan muokkauskenttään vihjeteksti ja määritellään tekstin riville vaihto.
        muokkausKentta.setPromptText(quickStartTeksti);
        muokkausKentta.setWrapText(true);
        // Ajetaan näyttökentän renderöinti kerran, jotta saadaan tervetuloteksti näkyviin.
        // Tässä ei voi käyttää valmista metodia, koska metodin käyttämä muokkauskenttä on tässä vaiheessa tyhjä.
        markdownParserOlio.setTeksti(tervetuloTeksti);
        markdownParserOlio.run();
        this.nayttoKentta.getEngine().loadContent(markdownParserOlio.getHtml());

        // Laitetaan nämä kaksi tekstikenttää HBox sisälle, jotta ne ovat tasavertaisia.
        this.paivitaKenttienKoko();
        this.setCenter(keskiosa);

        // Alapalkin säädöt
        Insets alapalkinInsets = new Insets(5, 5, 5, 5);
        alapalkinMittarit.setPadding(alapalkinInsets);
        alapalkinMittarit.getChildren().addAll(merkkiMaaraTeksti, sanaMaaraTeksti, riviMaaraTeksti, alapalkinStatus);
        this.paivitaAlapalkki(0, 0, 0);
        alapalkki.setPadding(alapalkinInsets);
        alapalkki.setLeft(alapalkinMittarit);
        alapalkki.setRight(markdownNappi);
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
    public Kayttoliittyma() {
        this.alustajaApuri();
    }

    /**
     * Alustaja, jolle voidaan syöttää aloitusteksti. Tätä voisi käyttää, jos halutaan, että ohjelma avaa viimeksi
     * käsitellyn tiedoston suoraan.
     *
     * @param tekstiIn Haluttu teksti String-oliona. Tämä teksti tulee näkyviin muokkauskenttään ja näyttökenttään.
     */
    public Kayttoliittyma(String tekstiIn) {
        this.alustajaApuri();
        muokkausKentta.setText(tekstiIn);
        // Ajetaan renderöinti kerran, jotta saadaan haluttu aloitusteksti suoraan näkyviin oikealle puolelle.
        this.naytaTekstiKasiteltyna();
    }

    /**
     * Asettaa annetun String-olion tekstin muokkauskentän tekstiksi ja päivittää oikean puolen näkymän.
     *
     * @param tekstiIn Haluttu teksti String-oliona.
     */
    public void setTeksti(String tekstiIn) {
        muokkausKentta.setText(tekstiIn);
        this.naytaTekstiKasiteltyna();
    }

    /**
     * Palauttaa muokkauskentän tekstin String-oliona.
     *
     * @return Muokkauskentän sisältämä teksti String-oliona.
     */
    public String getTeksti() {
        return muokkausKentta.getText();
    }

    /**
     * Kysyy käyttäjältä, minkä tiedoston tämä haluaa avata. Käytetään JavaFX:n FileChooser-toimintoa.
     *
     * Metodi voi antaa virheilmoituksen, jos tiedostonimissä tai -polussa on ongelmia.
     *
     * @param primaryStage Pääohjelman käyttämä (JavaFX) Stage-olio.
     * @return Halutun tiedoston sijainti String-oliona.
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
     * Metodi voi antaa virheilmoituksen, jos tiedostonimissä tai -polussa on ongelmia.
     *
     * @param primaryStage Pääohjelman käyttämä (JavaFX) Stage-olio.
     * @return Halutun tallennissijainti String-oliona.
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
     * Päivittää muokkaus- ja näyttökenttien leveyden vastaamaan puolta koko ikkunan leveydestä. Jos näkyvissä on vain
     * muokkauskenttä, sen koko venytetään koko ikkunan kokoiseksi. Tämä aktivoidaan aina, kun ikkunan kokoa muutetaan.
     */
    public void paivitaKenttienKoko() {
        if (onkoMarkdownNakyvissa) {
            muokkausKentta.setPrefWidth(this.getWidth() / 2);
            nayttoKentta.setPrefWidth(this.getWidth() / 2);
        } else {
            muokkausKentta.setPrefWidth(this.getWidth());
        }
    }

    /**
     * Syöttää muokkauskentän tekstin markdown-parserille, luo uuden säikeen ja ajaa parserin siinä.
     * Lopuksi syöttää parserin tuottaman html-stringin näyttökenttään.
     *
     * Metodi voi antaa virheilmoituksen, jos säikeen muodostamisessa ja ajossa tulee ongelmia.
     */
    public void naytaTekstiKasiteltyna() {
        // Ei tehdä mitään, jos edellinen renderöinti on vielä kesken.
        if (!markdownParserOlio.getRenderKesken()) {
            markdownParserOlio.setTeksti(this.getTeksti());
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
     * @param charMaara Näytettävä merkkimäärä int-muuttujana.
     * @param sanaMaara Näytettävä sanamäärä int-muuttujana.
     * @param riviMaara Näytettävä rivimäärä int-muuttujana.
     */
    public void paivitaAlapalkki(int charMaara, int sanaMaara, int riviMaara) {
        this.merkkiMaaraTeksti.setText("Merkkejä: " + charMaara);
        this.sanaMaaraTeksti.setText("Sanoja: " + sanaMaara);
        this.riviMaaraTeksti.setText("Rivejä: " + riviMaara);
    }

    /**
     * Päivittää alapalkin status-kenttään annetun tekstin.
     *
     * @param statusTeksti Näytettävä status-teksti String-oliona.
     */
    public void setAlapalkinStatus(String statusTeksti) {
        this.alapalkinStatus.setText(statusTeksti);
    }

    /**
     * Piilottaa oikealla puolella näkyvän markdown-näyttökentän ja muuttaa napin tekstin.
     */
    public void piilotaMarkdown() {
        this.keskiosa.getChildren().remove(nayttoKentta);
        this.onkoMarkdownNakyvissa = false;
        this.markdownNappi.setText("Näytä markdown");
        this.paivitaKenttienKoko();
    }

    /**
     * Tuo näkyviin oikean puolen markdown-näyttökentän ja muuttaa napin tekstin.
     */
    public void naytaMarkdown() {
        this.keskiosa.getChildren().add(nayttoKentta);
        this.onkoMarkdownNakyvissa = true;
        this.markdownNappi.setText("Piilota markdown");
        this.paivitaKenttienKoko();
        this.naytaTekstiKasiteltyna();
    }

    public boolean getOnkoMarkdownNakyvissa() {
        return onkoMarkdownNakyvissa;
    }
}
