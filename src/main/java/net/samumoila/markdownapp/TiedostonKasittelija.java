package net.samumoila.markdownapp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Lukee tiedoston tiedostopolusta ja palauttaa sen sisällön String-oliona. Tässä ohjelman versiossa tiedoston
 * sisällön täytyy olla luettavissa suoraan tekstinä. Tiedostopäätteellä sinänsä ei ole merkitystä, tosin
 * tiedostonvalitsija hakee oletuksena vain *.txt ja *.md tiedostopäätteitä.
 *
 * Tästä luokasta ei ole tarkoitus luoda oliota, vaan sen staattisia luokkia käytetään suoraan.
 */
class TiedostonKasittelija {

    /**
     * Palauttaa annetussa tiedostopolussa sijaitsevan tiedoston sisällön String-oliona.
     *
     * Metodi voi antaa virheilmoituksen, jos tiedoston lukemisessa on ongelmia.
     *
     * @param tiedostoPolku Tiedostopolku, missä tiedosto sijaitsee.
     * @return String-olio Tiedoston sisältämä teksti String-oliona.
     * @throws Exception
     */
    public static String lueTiedosto(String tiedostoPolku) {
        String palautusTeksti = "";
        try {
            // Tiedoston lukeminen on muokattu tämän sivun kohdasta 5:
            // https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
            palautusTeksti = new String(Files.readAllBytes(Paths.get(tiedostoPolku)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palautusTeksti;
    }

    /**
     * Tallentaa annetun String-olion tekstitiedostona annettuun tiedostopolkuun.
     *
     * Metodi voi antaa virheilmoituksen, jos annetun tiedostopolun kanssa on ongelmia.
     *
     * @param tallennettavaTeksti Tallennettava teksti String-oliona.
     * @param tiedostoPolku Tiedostopolku, mihin tiedosto tallennetaan.
     */
    public static void tallennaTiedosto (String tallennettavaTeksti, String tiedostoPolku) {
        try {
            PrintWriter kirjoittaja = new PrintWriter(tiedostoPolku);
            kirjoittaja.write(tallennettavaTeksti);
            kirjoittaja.close();
            System.out.println("Tallennus onnistui!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
