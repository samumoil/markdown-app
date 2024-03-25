module net.samumoila.markdownapp {
    requires javafx.controls;
    requires javafx.fxml;
    // Nämä tarvittiin tähän projektiin erityisesti.
    requires javafx.web;
    requires org.commonmark;

    opens net.samumoila.markdownapp to javafx.fxml;
    exports net.samumoila.markdownapp;
}