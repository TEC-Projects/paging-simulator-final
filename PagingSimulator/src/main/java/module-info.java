module com.pagingsimulator.pagingsimulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.pagingsimulator.pagingsimulator to javafx.fxml;
    exports com.pagingsimulator.pagingsimulator;
    exports com.pagingsimulator.pagingsimulator.UI.Controller;
    opens com.pagingsimulator.pagingsimulator.UI.Controller to javafx.fxml;
    opens com.pagingsimulator.pagingsimulator.Model to javafx.fxml;
    exports com.pagingsimulator.pagingsimulator.Model;
}