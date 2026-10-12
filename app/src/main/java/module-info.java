open module com.one_half_men.app {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires io.avaje.jsonb;
    requires io.avaje.json;

    provides io.avaje.jsonb.spi.JsonbExtension with com.one_half_men.app.jsonb.GeneratedJsonComponent;

    requires com.one_half_men.annotations;

    exports com.one_half_men.app;
}
