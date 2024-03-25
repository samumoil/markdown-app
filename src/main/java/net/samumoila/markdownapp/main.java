package net.samumoila.markdownapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class main extends Application{

    // Alustetaan käyttöliittymä.
    private KayttoLiittyma UI = new KayttoLiittyma();

    // Määritellään muistipaikka ja kutsumanimi käsiteltävälle tekstioliolle.
    private static Teksti kasiteltavaTekstiOlio;

    // Tähän sijoitetaan avatun TAI tallennetun tiedoston tiedostopolku.
    private static String valitunTiedostonPolku = "";

    public static void main(String[] args) {
        // Luodaan yksi olio valmiiksi, ennen kuin on mitään muita avattu.
        kasiteltavaTekstiOlio = new TekstiMarkdown();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        // VALIKOIDEN TOIMINNALLISUUDET:
        UI.avaaTiedosto.setOnAction(e -> {
            this.avaaTiedosto(primaryStage);
        });
        UI.tallennaTiedosto.setOnAction(e -> {
            this.tallennaTiedostoon(primaryStage);
        });
        UI.suljeSovellus.setOnAction(e -> {
            System.exit(0);
        });

        // PIKANÄPPÄINTEN TOIMINNALLISUUDET
        // Kopioitu kohdasta "Using accelerator"
        // https://medium.com/@zoha131/handling-keyboard-shortcuts-in-javafx-2972ba950a48
        KeyCombination pikaAvaa = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        Runnable runnableAvaa = ()-> this.avaaTiedosto(primaryStage);
        KeyCombination pikaTallenna = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        Runnable runnableTallenna = ()-> this.tallennaTiedostoon(primaryStage);

        // Seurataan ikkunan kokoa ja muutetaan tekstikenttien kokoa tarvittaessa.
        // Idea muokattu täältä: https://stackoverflow.com/questions/38216268/how-to-listen-resize-event-of-stage-in-javafx
        UI.widthProperty().addListener(e -> {
            UI.paivitaKenttienKoko();
        });

        // Päivitetään tekstioliota ja näyttökenttää jokaisen näppäinpainalluksen jälkeen.
        UI.muokkausKentta.setOnKeyTyped(e -> {
            kasiteltavaTekstiOlio.setTeksti(UI.getTeksti());
            UI.naytaTekstiKasiteltyna();
            UI.paivitaAlapalkki(kasiteltavaTekstiOlio.getCharMaara(), kasiteltavaTekstiOlio.getSanaMaara(), kasiteltavaTekstiOlio.getRiviMaara());
            UI.setAlapalkinStatus(""); // Tyhjennetään status, jos siellä on vaikka tallennuksen kuittaus.
        });

        // Luodaan kehys tässä vaiheessa, koska seuraavaksi pikanäppäimet tarvitsevat sitä.
        Scene kehys = new Scene(UI);

        // Kopioitu netistä, katso ylempänä "pikanäppäinten toiminnallisuudet".
        kehys.getAccelerators().put(pikaAvaa, runnableAvaa);
        kehys.getAccelerators().put(pikaTallenna, runnableTallenna);

        primaryStage.setScene(kehys);
        primaryStage.setTitle("Markdown-app");
        primaryStage.show();
    }

    private void avaaTiedosto(Stage primaryStage) {
        valitunTiedostonPolku = UI.kysyAvausSijainti(primaryStage); // Tähän täytyy syöttää Stage, koska FileChooser tarvitsee
        // Jos käyttäjä valitsi sijainnin, avataan tiedosto.
        if (!valitunTiedostonPolku.equals("")) {
            System.out.println("Opening file: " + valitunTiedostonPolku);
            kasiteltavaTekstiOlio.setTeksti(TiedostonKasittelija.lueTiedosto(valitunTiedostonPolku));
            UI.setTeksti(kasiteltavaTekstiOlio.getTeksti());
            UI.setAlapalkinStatus("Ladattu tiedosto: " + valitunTiedostonPolku);
        }
    }

    private void tallennaTiedostoon(Stage primaryStage) {
        kasiteltavaTekstiOlio.setTeksti(UI.getTeksti()); // Päivitetään teksti muokkauskentästä tekstioliolle.
        valitunTiedostonPolku = UI.kysyTallennusSijainti(primaryStage); // Tähän täytyy syöttää Stage, koska FileChooser tarvitsee
        // Jos käyttäjä valitsi sijainnin, tallennetaan tiedosto.
        if (!valitunTiedostonPolku.equals("")) {
            System.out.println("Saving to file: " + valitunTiedostonPolku);
            TiedostonKasittelija.tallennaTiedosto(kasiteltavaTekstiOlio.getTeksti(), valitunTiedostonPolku);
            UI.setAlapalkinStatus("Tallennettu tiedostoon: " + valitunTiedostonPolku);
        }
    }
}
