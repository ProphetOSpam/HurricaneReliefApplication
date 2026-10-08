module com.one_half_men.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires tools.jackson.core;
    requires tools.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    requires lombok;

    requires com.one_half_men.annotations;

    opens com.one_half_men.app to javafx.fxml;
    opens com.one_half_men.app.page to javafx.fxml;

    exports com.one_half_men.app;
}
