package net.samumoila.markdownapp;

/**
 * Abstrakti luokka, joka kuvastaa yksinkertaisinta mahdollista tekstiä.
 */
public abstract class Teksti implements TekstiRajapinta {
    private String paaTeksti;
    private int charMaara; // Tekstin merkkimäärä.
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
     * Alustaa tekstolion annetulla String-oliolla. Käytetään tiedostostalukemisen yhteydessä.
     *
     * @param teksti Tekstioliolle syötettävä teksti String-oliona.
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
        int sanat = 0;
        boolean uusiSanaAloitettu = false; // Onko tällä hetkellä sana kesken.
        boolean erikoismerkkiKohdattu = false; // Oliko edellinen merkki erikoismerkki.

        for (int i = 0; i < paaTeksti.length(); i++) {
            char comparisonChar = paaTeksti.charAt(i);

            if (!uusiSanaAloitettu) {
                if (Character.isLetterOrDigit(comparisonChar)) {
                    // Jos uusi sana ei vielä ollut kesken ja tarkasteltava merkki on kirjain tai numero,
                    // aloitetaan sana ja lisätään sanalaskuria.
                    uusiSanaAloitettu = true;
                    sanat++;
                }
            } else { // Sana on siis kesken.
                if (Character.isWhitespace(comparisonChar)) {
                    // Sana päättyy välilyöntiin ja muihin white space -merkkeihin.
                    uusiSanaAloitettu = false;
                    erikoismerkkiKohdattu = false;
                } else if (!Character.isLetterOrDigit(comparisonChar)) {
                    if (!erikoismerkkiKohdattu) {
                        // Kohdataan ensimmäinen erikoismerkki. Sana ei vielä pääty.
                        erikoismerkkiKohdattu = true;
                    } else {
                        // Kohdataan toinen erikoismerkki, sana päättyy.
                        erikoismerkkiKohdattu = false;
                        uusiSanaAloitettu = false;
                    }
                } else {
                    // Kohdattu merkki on siis kirjain tai numero. Ei tehdä mitään ja annetaan sanan jatkua.
                }
            }
        }
        this.sanaMaara = sanat;
    }

    /**
     * Lasketaan tekstin rivien määrä.
     */
    private void laskeRivit() {
        // Idea saatu täältä https://stackoverflow.com/a/50631407
        this.riviMaara = (int) paaTeksti.lines().count();
    }

    /**
     * Asetetaan uusi tekstiolion teksti. Päivitetään samalla laskurit,
     *
     * @param teksti String-oliona annettu teksti, joka päivittyy tekstiolion tekstiksi.
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
     * @return Tekstiolion sisältämä teksti String-oliona.
     */
    public String getTeksti() {
        return this.paaTeksti;
    }

    /**
     * Palautetaan tekstin merkkimäärä.
     *
     * @return Tekstin merkkimäärä int-muuttujana.
     */
    public int getCharMaara() {
        return this.charMaara;
    }

    /**
     * Palautetaan tekstin sanamäärä.
     *
     * @return Tekstin sanamäärä int-muuttajana.
     */
    public int getSanaMaara() {
        return this.sanaMaara;
    }

    /**
     * Palautetaan tekstin rivimäärä.
     *
     * @return Tekstin rivimäärä int-muuttujana.
     */
    public int getRiviMaara() {
        return this.riviMaara;
    }

    /**
     * Palautetaan tekstiolion teksti, sekä merkki-, rivi- ja sanamäärät String-oliona.
     *
     * @return Tekstiolion teksti sekä merkki-, sana- ja rivimäärät String-oliona.
     */
    @Override
    public String toString() {
        String palautus = getTeksti() +
                "\n" +
                "\nCharacter count:\t" + getCharMaara() +
                "\nRow count:\t\t\t" + getRiviMaara() +
                "\nWord count:\t\t\t" + getSanaMaara();
        return palautus;
    }
}
