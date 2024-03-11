package net.samumoila.markdownapp;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Lukee tiedoston tiedostopolusta ja palauttaa sen sisällön String-oliona.
 */
class MyFileReader {
    /**
     *
     * @param filePath
     * @return String-olio
     * @throws Exception
     */
    public static String readFile(String filePath) {
        String returnText = "";
        try {
            returnText = new String(Files.readAllBytes(Paths.get(filePath)));
            /*
             * Tiedoston lukeminen on kopioitu tämän sivuston kohdasta 5:
             * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
             */
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnText;
    }
    /*
    Tähän luodaan virheenhallinta. Palauttaa eri virhekoodeilla tiedon pääohjelmaan, mikä meni pieleen.
     */
}
