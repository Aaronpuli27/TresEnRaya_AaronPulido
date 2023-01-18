package com.example.tres_en_raya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TresEnRayaAplicacion extends Application {

    public static Stage principalStage;

    @Override
    public void start(Stage stage) throws Exception {
        principalStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TresEnRayaAplicacion.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(TresEnRayaAplicacion.class.getResource("style.css")).toExternalForm());
        System.out.println(Objects.requireNonNull(TresEnRayaAplicacion.class.getResource("style.css")).toExternalForm());

        principalStage.setTitle("Tres En Raya de Aaron Pulido");
        principalStage.setScene(scene);
        principalStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void replaceSceneContent(String fxml) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(TresEnRayaAplicacion.class.getResource(fxml)), null, new JavaFXBuilderFactory());
        Scene scene = TresEnRayaAplicacion.principalStage.getScene();
        if (scene == null) {
            System.out.println("SCENE");
            scene = new Scene(page);
            TresEnRayaAplicacion.principalStage.setScene(scene);
        } else {
            TresEnRayaAplicacion.principalStage.getScene().setRoot(page);
        }
        TresEnRayaAplicacion.principalStage.sizeToScene();

    }
}