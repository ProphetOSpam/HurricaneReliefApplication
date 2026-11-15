package com.one_half_men.gui;

import java.io.IOException;

import com.one_half_men.app.hurricane_application.HurricaneApplication;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        CSSFX.start();

        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build()
                .setGlobal();

        scene = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.setTitle("Hurricane Application");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        HurricaneApplication.getInstance().write();
    }

    public static void setRoot(String fxml) {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
