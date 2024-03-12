package net.samumoila.markdownapp;

/**
 * Kuvastaa yksinkertaisinta mahdollista tekstiä, esimerkiksi txt-tiedostoon tallennettua.
 */
public class TextSimple implements Text {
    private String mainText;
    private int charCount;
    private int wordCount;
    private int rowCount;

    /**
     * Oletusalustaja, jolla teksti on tyhjä.
     */
    public TextSimple() {
        this.mainText = "";
        this.countChars();
        this.countRows();
        this.countWords();
    }

    /**
     * Alustaa tekstin annetulla String-oliolla. Käytetään tiedostostalukemisen yhteydessä.
     *
     * @param teksti
     */
    public TextSimple(String teksti) {
        this.mainText = teksti;
        this.countChars();
        this.countRows();
        this.countWords();
    }

    private void countChars() {
        this.charCount = this.mainText.length();
    }

    /**
     * Lasketaan tekstissä olevien sanojen määrä.
     *
     * Laskentalogiikkaan on otettu mallia ghostwriter-ohjelmasta:
     *  - Sana päättyy, jos tulee joku "white space"-merkki.
     *  - Sana päättyy, jos tulee kaksi erikoismerkkiä peräkkäin.
     */
    private void countWords() {
        int words = 0;
        boolean newWordStarted = false; // Onko tällä hetkellä sana kesken.
        boolean specialCharEncountered = false; // Oliko edellinen merkki erikoismerkki.

        for (int i = 0; i < mainText.length(); i++) {
            char comparisonChar = mainText.charAt(i);

            if (!newWordStarted) {
                if (Character.isLetterOrDigit(comparisonChar)) {
                    // Jos uusi sana ei vielä ollut kesken ja tarkasteltava merkki on kirjain tai numero,
                    // aloitetaan sana ja lisätään sanalaskuria.
                    newWordStarted = true;
                    words++;
                }
            } else { // Sana on siis kesken.
                System.out.println(comparisonChar);
                if (Character.isWhitespace(comparisonChar)) {
                    // Sana päättyy välilyöntiin ja muihin white space -merkkeihin.
                    newWordStarted = false;
                    specialCharEncountered = false;
                } else if (!Character.isLetterOrDigit(comparisonChar)) {
                    if (!specialCharEncountered) {
                        // Kohdataan ensimmäinen erikoismerkki. Sana ei vielä pääty.
                        specialCharEncountered = true;
                    } else {
                        // Kohdataan toinen erikoismerkki, sana päättyy.
                        specialCharEncountered = false;
                        newWordStarted = false;
                    }
                } else {
                    // Kohdattu merkki on siis kirjain tai numero. Ei tehdä mitään ja annetaan sanan jatkua.
                }
            }
        }
        this.wordCount = words;
    }

    private void countRows() {
        // https://stackoverflow.com/a/50631407
        this.rowCount = (int) mainText.lines().count();
    }

    /**
     * Asetetaan uusi tekstiolion teksti. Päivitetään samalla laskurit,
     *
     * @param teksti
     */
    public void setText(String teksti) {
        this.mainText = teksti;
        this.countChars();
        this.countRows();
        this.countWords();
    }

    /**
     * Palautetaan tekstiolion teksti String-oliona.
     *
     * @return
     */
    public String getText() {
        return this.mainText;
    }

    /**
     * Palautetaan tekstin merkkimäärä.
     *
     * @return
     */
    public int getCharCount() {
        return this.charCount;
    }

    /**
     * Palautetaan tekstin sanamäärä.
     *
     * @return
     */
    public int getWordCount() {
        return this.wordCount;
    }

    /**
     * Palautetaan tekstin rivimäärä.
     *
     * @return
     */
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Palautetaan tekstiolion teksti, sekä merkki-, rivi- ja sanamäärät String-oliona.
     *
     * @return
     */
    @Override
    public String toString() {
        String text = getText() +
                "\nCharacter count:\t" + getCharCount() +
                "\nRow count:\t\t\t" + getRowCount() +
                "\nWord count:\t\t\t" + getWordCount();
        return text;
    }
}
