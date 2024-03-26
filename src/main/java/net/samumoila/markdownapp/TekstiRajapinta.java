package net.samumoila.markdownapp;

/**
 * Tämä rajapinta on olemassa, jotta tulevaisuudessa ohjelman käyttöä voi laajentaa muihinkin tiedostomuotoihin
 * kuin "txt" ja "md".
 */
public interface TekstiRajapinta {

    /**
     * Asetetaan uusi tekstiolion teksti.
     *
     * @param teksti String-oliona annettu teksti, joka päivittyy tekstiolion tekstiksi.
     */
    void setTeksti(String teksti);

    /**
     * Palautetaan tekstiolion teksti String-oliona.
     *
     * @return Tekstiolion sisältämä teksti String-oliona.
     */
    String getTeksti();

    /**
     * Palautetaan tekstin merkkimäärä.
     *
     * @return Tekstin merkkimäärä int-muuttujana.
     */
    int getCharMaara();

    /**
     * Palautetaan tekstin sanamäärä.
     *
     * @return Tekstin sanamäärä int-muuttajana.
     */
    int getSanaMaara();

    /**
     * Palautetaan tekstin rivimäärä.
     *
     * @return Tekstin rivimäärä int-muuttujana.
     */
    int getRiviMaara();
}
