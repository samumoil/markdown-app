/**
 * Kouluprojekti ohjelmointikurssille. Tämä ohjelma lukee ja tallentaa tekstitiedostoja, ja näyttää ne markdown-muodossa.
 */
module net.samumoila.markdownapp {
    requires javafx.controls;

    // Nämä tarvittiin tähän projektiin erityisesti.
    requires javafx.web;
    requires org.commonmark;

    exports net.samumoila.markdownapp;
}