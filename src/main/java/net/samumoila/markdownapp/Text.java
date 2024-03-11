package net.samumoila.markdownapp;

/**
 * Tämä rajapinta on olemassa, jotta tulevaisuudessa ohjelman käyttöä voi laajentaa muihinkin tiedostomuotoihin
 * kuin "txt" ja "md".
 */
public interface Text {

    void setText(String teksti);

    String getText();
    int getCharCount();
    int getWordCount();
    int getRowCount();
}
