module com.one_half_men.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires tools.jackson.core;
    requires tools.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    requires lombok;

    requires com.one_half_men.annotations;
    requires google.maps.services;

    opens com.one_half_men.app to javafx.fxml;

    exports com.one_half_men.app;
}
