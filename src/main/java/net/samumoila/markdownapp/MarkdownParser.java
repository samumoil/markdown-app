package net.samumoila.markdownapp;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownParser implements Runnable {
    // Nämä kolme riviä ovat lähes suoraan markdown-parserin ohjeesta
    // https://github.com/commonmark/commonmark-java?tab=readme-ov-file#parse-and-render-to-html
    private Parser parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();
    private Node document;

    private String html = "";
    private boolean renderKesken = false;

    /**
     * Alustaja ilman annettua tekstiä.
     */
    public MarkdownParser() {
        this.document = parser.parse("");
    }

    /**
     * Alustaja, jolle voidaan syöttää teksti jo luonnin yhteydessä.
     *
     * @param tekstiIn
     */
    public MarkdownParser(String tekstiIn) {
        this.document = parser.parse(tekstiIn);
    }

    /**
     * Asettaa oliolle käsiteltävän tekstin String-oliona.
     *
     * @param teksti
     */
    public void setText(String teksti) {
        this.document = parser.parse(teksti);
    }

    /**
     * Palauttaa olion tuottaman HTML-version aiemmin annetusta tekstistä.
     *
     * @return
     */
    public String getHtml() {
        return html;
    }

    /**
     * Palauttaa boolean-arvona tiedon, onko oliolla tekstin renderöinti kesken.
     *
     * @return
     */
    public boolean getRenderKesken() {
        return renderKesken;
    }

    /**
     * Runnable-rajapinnan totettamiseen tarkoitettu metodi, jolla suoritetaan aiemmin annetun tekstin renderöinti.
     */
    @Override
    public void run() {
        renderKesken = true;
        this.html = renderer.render(document);
        renderKesken = false;
    }
}
