module com.one_half_men {
    requires javafx.controls;
    requires javafx.fxml;
    requires tools.jackson.core;
    requires tools.jackson.databind;
    requires com.fasterxml.jackson.annotation;

    opens com.one_half_men to javafx.fxml;

    exports com.one_half_men;
}
