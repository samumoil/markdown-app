package net.samumoila.markdownapp;

/**
 * Tämä rajapinta on olemassa, jotta tulevaisuudessa ohjelman käyttöä voi laajentaa muihinkin tiedostomuotoihin
 * kuin "txt" ja "md".
 */
public interface TekstiRajapinta {

    void setTeksti(String teksti);

    String getTeksti();
    int getCharMaara();
    int getSanaMaara();
    int getRiviMaara();
}
