package net.samumoila.markdownapp;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class markdownParser implements Runnable {
    // Nämä kolme riviä ovat lähes suoraan markdown-parserin ohjeesta
    // https://github.com/commonmark/commonmark-java?tab=readme-ov-file#parse-and-render-to-html

    private Parser parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();
    private Node document;
    private String html = "";
    private boolean renderKesken = false;

    public markdownParser() {
        this.document = parser.parse("");
    }

    public markdownParser(String tekstiIn) {
        this.document = parser.parse(tekstiIn);
    }

    public void setText(String teksti) {
        this.document = parser.parse(teksti);
    }

    public String getHtml() {
        return html;
    }

    public boolean getRenderKesken() {
        return renderKesken;
    }

    @Override
    public void run() {
        renderKesken = true;
        this.html = renderer.render(document);
        renderKesken = false;
    }
}
