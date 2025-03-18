module aureo.anaerosync {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires annotations;
    requires static lombok;

    opens aureo.anaerosync to javafx.fxml;
    exports aureo.anaerosync;
    exports aureo.anaerosync.squares;
    opens aureo.anaerosync.squares to javafx.fxml;
}