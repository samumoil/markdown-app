package net.samumoila.markdownapp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Lukee tiedoston tiedostopolusta ja palauttaa sen sisällön String-oliona.
 */
class TiedostonKasittelija {
    /**
     *
     * @param tiedostoPolku
     * @return String-olio
     * @throws Exception
     */
    public static String lueTiedosto(String tiedostoPolku) {
        String palautusTeksti = "";
        try {
            // Tiedoston lukeminen on kopioitu tämän sivun kohdasta 5:
            // https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
            palautusTeksti = new String(Files.readAllBytes(Paths.get(tiedostoPolku)));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return palautusTeksti;
    }
    /*
    Tähän luodaan virheenhallinta. Palauttaa eri virhekoodeilla tiedon pääohjelmaan, mikä meni pieleen.
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
