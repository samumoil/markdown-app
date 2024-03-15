package net.samumoila.markdownapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class main extends Application{

    // Alustetaan käyttöliittymä.
    KayttoLiittyma UI = new KayttoLiittyma();
    // Määritellään muistipaikka ja kutsumanimi käsiteltävälle tekstioliolle.
    static Teksti kasiteltavaTekstiOlio;
    // Tähän sijoitetaan avatun TAI tallennetun tiedoston tiedostopolku.
    static String valitunTiedostonPolku = "";

    public static void main(String[] args) {
        // Luodaan yksi olio valmiiksi, ennen kuin on mitään muita avattu.
        kasiteltavaTekstiOlio = new TekstiMarkdown();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // Laitetaan teksti näkyviin isoon kirjoituskenttään.
        UI.setTeksti(kasiteltavaTekstiOlio.getTeksti());
        Markdownifier.muunnaMarkdown(kasiteltavaTekstiOlio.getTeksti(), UI.nayttoKentta);

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

        // Seurataan ikkunan kokoa ja muutetaan tekstikenttien kokoa tarvittaessa.
        // Idea muokattu täältä: https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
        UI.widthProperty().addListener(e -> {
            UI.paivitaKenttienKoko();
        });

        // Päivitetään tekstioliota ja näyttökenttää jokaisen näppäinpainalluksen jälkeen.
        UI.muokkausKentta.setOnKeyTyped(e -> {
            kasiteltavaTekstiOlio.setTeksti(UI.muokkausKentta.getText());
            Markdownifier.muunnaMarkdown(kasiteltavaTekstiOlio.getTeksti(), UI.nayttoKentta);
        });

        Scene kehys = new Scene(UI);
        primaryStage.setScene(kehys);
        primaryStage.setTitle("Markdown-app");
        primaryStage.show();
    }
}
