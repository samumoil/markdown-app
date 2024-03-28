package net.samumoila.markdownapp;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Luokan oliolle syötetään String-oliona teksti, joka renderöidään ajamalla olion run()-metodi. Tämän jälkeen
 * renderöity teksti saadaan ulos getHtml-metodilla, joka palauttaa html-muotoisen tekstin String-oliona.
 *
 * Parseri on nimeltään <a href="https://github.com/commonmark/commonmark-java?tab=readme-ov-file#parse-and-render-to-html">commonmark-java</a>.
 * @see <a href="https://github.com/commonmark/commonmark-java?tab=readme-ov-file#parse-and-render-to-html">Commonmark-java</a>
 */
public class MarkdownParser implements Runnable {
    // Nämä kolme riviä ovat lähes suoraan markdown-parserin ohjeesta
    private Parser parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();
    private Node teksti;

    private String html = "";
    private boolean renderKesken = false;

    /**
     * Alustaja ilman annettua tekstiä.
     */
    public MarkdownParser() {
        this.teksti = parser.parse("");
    }

    /**
     * Alustaja, jolle voidaan syöttää teksti jo luonnin yhteydessä.
     *
     * @param tekstiIn Alustajalle syötettävä teksti String-oliona.
     */
    public MarkdownParser(String tekstiIn) {
        this.teksti = parser.parse(tekstiIn);
    }

    /**
     * Asettaa käsiteltävän tekstin String-oliona.
     *
     * @param teksti Käsiteltävä teksti String-oliona.
     */
    public void setTeksti(String teksti) {
        this.teksti = parser.parse(teksti);
    }

    /**
     * Palauttaa olion tuottaman HTML-version aiemmin annetusta tekstistä. Tätä ennen on täytynyt ajaa renderöinti
     * run()-metodilla.
     *
     * @return HTML-muotoinen teksti String-oliona.
     */
    public String getHtml() {
        return html;
    }

    /**
     * Palauttaa boolean-arvona tiedon, onko oliolla tekstin renderöinti kesken.
     *
     * @return Tieto boolean-arvona, onko oliolla renderöinti kesken.
     */
    public boolean getRenderKesken() {
        return renderKesken;
    }

    /**
     * Runnable-rajapinnan totettamiseen tarkoitettu metodi, jolla suoritetaan aiemmin annetun tekstin renderöinti.
     * Renderöinnin tulos syötetään html-muuttujaan, joka täytyy vielä hakea erikseen getHtml-metodilla.
     */
    @Override
    public void run() {
        renderKesken = true;
        this.html = renderer.render(teksti);
        renderKesken = false;
    }
}
