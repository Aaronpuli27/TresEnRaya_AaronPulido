package com.example.tres_en_raya.controller;

import com.example.tres_en_raya.TresEnRayaAplicacion;
import com.example.tres_en_raya.model.FicheroCSV;
import com.example.tres_en_raya.model.Estadisticas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.GridPane.*;

public class ControladorDeEstadisticas implements Initializable {
    @FXML
    GridPane statsGrid;
    protected static HashMap<String, Estadisticas> statsList = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridConstructor();
    }


    @FXML
    public void gridConstructor() {
        int rows = 1;
        for (Estadisticas stat : statsList.values()) {
            Label name = new Label(stat.getNombre());
            Label wins = new Label(stat.getVictoria() + "");
            Label loses = new Label(stat.getDerrota() + "");
            Label tied = new Label(stat.getEmpate() + "");

            statsGrid.add(name, 0, rows);
            statsGrid.add(wins, 1, rows);
            statsGrid.add(loses, 2, rows);
            statsGrid.add(tied, 3, rows);

            setHalignment(name, HPos.CENTER);
            setHalignment(wins, HPos.CENTER);
            setHalignment(loses, HPos.CENTER);
            setHalignment(tied, HPos.CENTER);
            rows++;
        }
    }

    @FXML
    protected void onClickBackBtn() {
        try {
            TresEnRayaAplicacion.replaceSceneContent("main-window.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static void readStats() {
        List<String[]> listStatsReaded;
        try {
            listStatsReaded = FicheroCSV.readCSV("src/main/resources/data/stats.csv");
            listStatsReaded.forEach(strings -> statsList.put(strings[0], new Estadisticas(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]))));
        } catch (IOException e) {
            System.out.println("No hay estadisticas");
        }
    }

}
