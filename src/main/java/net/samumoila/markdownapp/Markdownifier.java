package net.samumoila.markdownapp;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Markdownifier {

    public static void muunnaMarkdown(String tekstiIn, TextFlow textFlowOlio) {

        // TÄMÄ ON VÄLIAIKAINEN:
        // Tyhjennetään edellisen kierroksen tulokset
        textFlowOlio.getChildren().clear();
        // Näytetään uusi teksti
        textFlowOlio.getChildren().addAll(new Text(tekstiIn));
    }
}
