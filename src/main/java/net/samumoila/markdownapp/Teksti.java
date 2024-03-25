package net.samumoila.markdownapp;

/**
 * Kuvastaa yksinkertaisinta mahdollista tekstiä, esimerkiksi txt-tiedostoon tallennettua.
 */
public abstract class Teksti implements TekstiRajapinta {
    private String paaTeksti;
    private int charMaara;
    private int sanaMaara;
    private int riviMaara;

    /**
     * Oletusalustaja, jolla teksti on tyhjä.
     */
    public Teksti() {
        this.paaTeksti = "";
        this.laskeChar();
        this.laskeRivit();
        this.laskeSanat();
    }

    /**
     * Alustaa tekstin annetulla String-oliolla. Käytetään tiedostostalukemisen yhteydessä.
     *
     * @param teksti
     */
    public Teksti(String teksti) {
        this.paaTeksti = teksti;
        this.laskeChar();
        this.laskeRivit();
        this.laskeSanat();
    }

    /**
     * Lasketaan tekstissä olevien merkkien määrä.
     */
    private void laskeChar() {
        this.charMaara = this.paaTeksti.length();
    }

    /**
     * Lasketaan tekstissä olevien sanojen määrä.
     *
     * Laskentalogiikkaan on otettu mallia ghostwriter-ohjelmasta:
     *  - Sana päättyy, jos tulee joku "white space"-merkki.
     *  - Sana päättyy, jos tulee kaksi erikoismerkkiä peräkkäin.
     */
    private void laskeSanat() {
        int words = 0;
        boolean newWordStarted = false; // Onko tällä hetkellä sana kesken.
        boolean specialCharEncountered = false; // Oliko edellinen merkki erikoismerkki.

        for (int i = 0; i < paaTeksti.length(); i++) {
            char comparisonChar = paaTeksti.charAt(i);

            if (!newWordStarted) {
                if (Character.isLetterOrDigit(comparisonChar)) {
                    // Jos uusi sana ei vielä ollut kesken ja tarkasteltava merkki on kirjain tai numero,
                    // aloitetaan sana ja lisätään sanalaskuria.
                    newWordStarted = true;
                    words++;
                }
            } else { // Sana on siis kesken.
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
        this.sanaMaara = words;
    }

    /**
     * Lasketaan tekstin rivien määrä.
     */
    private void laskeRivit() {
        // https://stackoverflow.com/a/50631407
        this.riviMaara = (int) paaTeksti.lines().count();
    }

    /**
     * Asetetaan uusi tekstiolion teksti. Päivitetään samalla laskurit,
     *
     * @param teksti
     */
    public void setTeksti(String teksti) {
        this.paaTeksti = teksti;
        this.laskeChar();
        this.laskeRivit();
        this.laskeSanat();
    }

    /**
     * Palautetaan tekstiolion teksti String-oliona.
     *
     * @return
     */
    public String getTeksti() {
        return this.paaTeksti;
    }

    /**
     * Palautetaan tekstin merkkimäärä.
     *
     * @return
     */
    public int getCharMaara() {
        return this.charMaara;
    }

    /**
     * Palautetaan tekstin sanamäärä.
     *
     * @return
     */
    public int getSanaMaara() {
        return this.sanaMaara;
    }

    /**
     * Palautetaan tekstin rivimäärä.
     *
     * @return
     */
    public int getRiviMaara() {
        return this.riviMaara;
    }

    /**
     * Palautetaan tekstiolion teksti, sekä merkki-, rivi- ja sanamäärät String-oliona.
     *
     * @return
     */
    @Override
    public String toString() {
        String text = getTeksti() +
                "\n" +
                "\nCharacter count:\t" + getCharMaara() +
                "\nRow count:\t\t\t" + getRiviMaara() +
                "\nWord count:\t\t\t" + getSanaMaara();
        return text;
    }
}
