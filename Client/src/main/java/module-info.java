module com.cstorage.cstorage {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires netty.all;


    opens com.cstorage.cstorage to javafx.fxml;
    exports com.cstorage.cstorage;
}