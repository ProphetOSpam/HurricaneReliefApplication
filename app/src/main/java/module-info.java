open module com.one_half_men.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;

    requires lombok;

    requires io.avaje.jsonb;
    requires io.avaje.json;

    provides io.avaje.jsonb.spi.JsonbExtension with com.one_half_men.app.jsonb.GeneratedJsonComponent;

    requires com.one_half_men.annotations;
    requires io.avaje.json.node;

    exports com.one_half_men.app;
    exports com.one_half_men.app.distance;
    exports com.one_half_men.app.location;
    exports com.one_half_men.app.request;
    exports com.one_half_men.app.shelter;
}
