module com.one_half_men {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.one_half_men to javafx.fxml;
    exports com.one_half_men;
}
