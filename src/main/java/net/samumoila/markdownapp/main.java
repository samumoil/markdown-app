package net.samumoila.markdownapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class main extends Application{

    // Alustetaan käyttöliittymä.
    KayttoLiittyma UI = new KayttoLiittyma();
    // Määritellään muistipaikka ja kutsumanimi käsiteltävälle tekstioliolle.
    static Teksti kasiteltavaTekstiOlio;
    // Näihin muuttujiin sijoitetaan avattu TAI tallennettu tiedosto ja sen polku.
    static File valittuTiedostoOlio;
    static String valitunTiedostonPolku = "";
    static String valittuKansio = "";


    // TÄMÄ METODI POISTETAAN LOPULLISESTA VERSIOSTA
    private static void testiAsioidenAlustusta() {
        valitunTiedostonPolku = "";
        // Luodaan testailua varten markdownteksti-olio.
        //kasiteltavaTekstiOlio = new TekstiMarkdown(TiedostonKasittelija.lueTiedosto("README.md"));
        kasiteltavaTekstiOlio = new TekstiMarkdown();

    }
    public static void main(String[] args) {
        testiAsioidenAlustusta();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // Laitetaan teksti näkyviin isoon kirjoituskenttään.
        UI.setTeksti(kasiteltavaTekstiOlio.getTeksti());

        // VALIKOIDEN TOIMINNALLISUUDET:
        UI.avaaTiedosto.setOnAction(e -> {
            valitunTiedostonPolku = UI.kysyAvausSijainti(primaryStage); // Tähän täytyy syöttää Stage, koska FileChooser tarvitsee
            System.out.println("Opening file: " + valitunTiedostonPolku);
            kasiteltavaTekstiOlio.setTeksti(TiedostonKasittelija.lueTiedosto(valitunTiedostonPolku));
            UI.setTeksti(kasiteltavaTekstiOlio.getTeksti());
        });
        UI.tallennaTiedosto.setOnAction(e -> {
            kasiteltavaTekstiOlio.setTeksti(UI.getTeksti()); // Päivitetään teksti muokkauskentästä tekstioliolle.
            valitunTiedostonPolku = UI.kysyTallennusSijainti(primaryStage); // Tähän täytyy syöttää Stage, koska FileChooser tarvitsee
            System.out.println("Saving to file: " + valitunTiedostonPolku);
            TiedostonKasittelija.tallennaTiedosto(kasiteltavaTekstiOlio.getTeksti(), valitunTiedostonPolku);
        });
        UI.suljeSovellus.setOnAction(e -> {
            System.exit(0);
        });

        Scene kehys = new Scene(UI, 600, 500);
        primaryStage.setScene(kehys);
        primaryStage.setTitle("Markdown-app");
        primaryStage.show();
    }
}
