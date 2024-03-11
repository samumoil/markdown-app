package net.samumoila.markdownapp;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Kuvastaa yksinkertaisinta mahdollista tekstiä, esimerkiksi txt-tiedostoon tallennettua.
 */
public class TextSimple implements Text {
    private String mainText;
    private int charCount;
    private int wordCount;
    private int rowCount;
    //private StringCharacterIterator iterator = new StringCharacterIterator(mainText);


    /**
     * Oletusalustaja
     */
    public TextSimple() {
        this.mainText = "Lorem Ipsum";
        this.countChars();
        this.countRows();
        this.countWords();
    }

    /**
     * Alustaa tekstin annetulla String-oliolla. MyFileReaderin käyttöä varten.
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
        StringCharacterIterator iterator = new StringCharacterIterator(mainText);
        int words = 0;
        StringBuilder word = new StringBuilder();
        char lastCharacter;
        iterator.first(); // Varmistetaan, että ollaan tekstin alussa.

        // String-olion läpikäyminen while-loopilla on kopioitu tämän ohjeen toisesta metodista:
        // https://www.geeksforgeeks.org/java-program-to-iterate-over-characters-in-string/
        while (iterator.current() != CharacterIterator.DONE) {
            String comparisonString = Character.toString(iterator.current());
            if (Character.isLetterOrDigit(iterator.current())) {
                // Jos tutkittava merkki on kirjain ja numero,
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
            this.wordCount = words;
        }
    }

    private void countRows() {
        StringCharacterIterator iterator = new StringCharacterIterator(mainText);
        int rows = 0;
        iterator.first();

        while (iterator.current() != CharacterIterator.DONE) {
            String comparisonString = Character.toString(iterator.current());
            comparisonString = "\n";
            if (comparisonString.equals("\n") ||
                    comparisonString.equals("\r") ||
                    comparisonString.equals("\r\n")) {
                // Jos tutkittava merkki on rivinvaihto, lisää rivilukumäärää.
                rows++;
            }
            rows++;
            iterator.next();

        }
        this.rowCount = rows;
    }

    public void setText(String teksti) {
        this.mainText = teksti;
        this.countChars();
        this.countRows();
        this.countWords();
    }

    public String getText() {
        return this.mainText;
    }
    public int getCharCount() {
        return this.charCount;
    }
    public int getWordCount() {
        return this.wordCount;
    }
    public int getRowCount() {
        return this.rowCount;
    }

    @Override
    public String toString() {
        return mainText;
    }
}
