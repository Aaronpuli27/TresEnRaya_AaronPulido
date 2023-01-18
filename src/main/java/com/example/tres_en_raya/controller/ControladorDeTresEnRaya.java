package com.example.tres_en_raya.controller;

import com.example.tres_en_raya.TresEnRayaAplicacion;
import com.example.tres_en_raya.model.FicheroCSV;
import com.example.tres_en_raya.model.Estadisticas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static com.example.tres_en_raya.controller.ControladorDeEstadisticas.statsList;

public class ControladorDeTresEnRaya implements Initializable {
    String turno = "";
    String victoriaJugador;
    boolean ganador = false;
    int maquina = 0;
    boolean turnoMaqina;
    String[][] tablaDeJuegoString;
    @FXML
    GridPane tablaDeJuego;
    @FXML
    Button botonInicio;
    @FXML
    Text turnoDeJugador;
    @FXML
    ToggleGroup modoDeJuego;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControladorDeEstadisticas.readStats();
    }

    @FXML
    protected void onClickStartBtn() {
        RadioButton gameMenuButton = (RadioButton) modoDeJuego.getSelectedToggle();
        maquina = 0;
        turnoMaqina = true;

        if (gameMenuButton == null) {
            turnoDeJugador.setText("Selecciona una opcion!");
            turnoDeJugador.setStyle("-fx-fill: red;");
        } else {
            restartTable("first");

            tablaDeJuegoString = new String[tablaDeJuego.getColumnCount()][tablaDeJuego.getRowCount()];
            turno = "X";
            botonInicio.setDisable(true);
            turnoDeJugador.setText("¡Es turno de " + turno + "!");
            turnoDeJugador.setStyle("-fx-fill: black;");
            switch (gameMenuButton.getId()) {
                case "compvscomp" -> {
                    maquina = 2;
                    moveIA();
                }
                case "plavscomp" -> maquina = 1;
            }
        }
    }


    @FXML
    protected void onClickCell(@NotNull ActionEvent event) {
        Button btn = (Button) event.getSource();
        btn.setText(turno);
        btn.setDisable(true);
        updateTablero();
        victoriaJugador = checkWin();
        if (victoriaJugador != null) {
            winnerStage(victoriaJugador);
            turnoDeJugador.setText("Siguiente juego...");
        } else changePlayer();
    }

    @FXML
    protected void onClickStatsBtn() {
        try {
            TresEnRayaAplicacion.replaceSceneContent("stats-window.fxml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onClickAboutBtnMenu() {
        Stage stage = new Stage();
        Label label = new Label("""
                \s
                    * Creado por: Aarón Pulido *
                \s""");
        label.setTextAlignment(TextAlignment.CENTER);
        ImageView imageView = new ImageView("https://elpuig.xeill.net/logo.png");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(250);

        VBox vBox = new VBox(label, imageView);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10, 20, 20, 20));
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("About");
        stage.show();
    }


    /**
     * Reinicia la tabla para empezar otra partida.
     * @param from String define si es la primera partida.
     */
    protected void restartTable(String from) {
        for (Node node : tablaDeJuego.getChildren()) {
            Button btnCell = (Button) node;
            if (from.equals("first")) {
                btnCell.setDisable(false);
                btnCell.setText("");
            } else {
                btnCell.setDisable(true);
                botonInicio.setDisable(false);
            }
        }
    }

    /**
     * Actualiza la tabla
     */
    protected void updateTablero() {
        List<Node> nodos = tablaDeJuego.getChildren();
        int pos = 0;
        for (int i = 0; i < tablaDeJuego.getColumnCount(); i++) {
            for (int j = 0; j < tablaDeJuego.getRowCount(); j++) {
                Button btnCell = (Button) nodos.get(pos);
                tablaDeJuegoString[i][j] = btnCell.getText();
                pos++;
            }
        }
    }

    /**
     * Detecta si es turno de la maquina.
     */
    protected void changePlayer() {
        if (turno.equals("X")) turno = "O";
        else turno = "X";
        turnoDeJugador.setText("¡Es turno de " + turno + "!");

        if (maquina == 1) {
            if (turnoMaqina) {
                moveIA();
            } else {
                turnoMaqina = true;
            }
        }
        else if (maquina == 2) {
            moveIA();
        }
    }

    /**
     * Metodo para idicar que la maquina haga un movimientio y revise si se puede poner en una casilla.
     */
    protected void moveIA() {
        List<Node> nodes = tablaDeJuego.getChildren();
        boolean clicked;
        int randomBtnClick;
        turnoMaqina = false;

        do {
            clicked = false;
            randomBtnClick = (int) (Math.random() * 9);
            Button btnCell = (Button) nodes.get(randomBtnClick);
            if (!btnCell.isDisabled()) {
                btnCell.fire();
            } else {
                clicked = true;
            }

        } while (clicked);


    }

    protected void winnerStage(String player) {
        Insets indvPadding = new Insets(10, 10, 10, 10);
        Stage winnerStage = new Stage();

        Text winnerText = new Text();
        ImageView imageView = new ImageView("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.es%2Ficono-gratis%2Ftic-tac-toe_566294&psig=AOvVaw2zjfjmXLE071GFj9sqD-r8&ust=1674151892431000&source=images&cd=vfe&ved=0CA0QjRxqFwoTCPj4_fvb0fwCFQAAAAAdAAAAABAD");
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(65);
        HBox hBox = new HBox(winnerText, imageView);
        hBox.setPadding(indvPadding);
        hBox.setSpacing(35);
        hBox.setAlignment(Pos.CENTER);

        Label playerXLabel = new Label("Player X Name:");
        TextField playerXName = new TextField();
        playerXName.setPrefHeight(10);
        playerXName.setPrefWidth(145);
        HBox hBox1 = new HBox(playerXLabel, playerXName);
        hBox1.setPadding(indvPadding);
        hBox1.setPrefHeight(30);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);

        Label playerOLabel = new Label("Player O Name:");
        TextField playerOName = new TextField();
        playerOName.setPrefHeight(15);
        playerOName.setPrefWidth(145);
        HBox hBox2 = new HBox(playerOLabel, playerOName);
        hBox2.setPadding(indvPadding);
        hBox2.setSpacing(10);
        hBox2.setAlignment(Pos.CENTER);

        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(60);
        submitBtn.onActionProperty().set(e -> {
            if (player.equals("X")) {
                submitStats(playerXName.getText().trim(), "win");
                submitStats(playerOName.getText().trim(), "lose");
            } else if (player.equals("O")) {
                submitStats(playerXName.getText().trim(), "lose");
                submitStats(playerOName.getText().trim(), "win");
            } else {
                submitStats(playerXName.getText().trim(), "tie");
                submitStats(playerOName.getText().trim(), "tie");
            }
            winnerStage.close();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.onActionProperty().set(e -> winnerStage.close());
        cancelBtn.setPrefWidth(60);
        HBox buttonsBox = new HBox(submitBtn, cancelBtn);
        buttonsBox.setSpacing(10);
        buttonsBox.setPadding(indvPadding);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        if (maquina >= 1) {
            playerOName.setDisable(true);
            if (maquina == 2) {
                playerXName.setDisable(true);
                submitBtn.setDisable(true);
            }
        }

        VBox vBox = new VBox(hBox, hBox1, hBox2, buttonsBox);
        Scene scene = new Scene(vBox);
        winnerStage.setTitle("¡Parida finalizada!");
        winnerStage.setScene(scene);
        winnerStage.show();

        if (player.equals("noBlanks")) {
            winnerText.setText("No ha ganado nadie");
        } else {
            winnerText.setText("¡El jugador " + player + " ha GANADO!");
        }


        restartTable("none");
    }

    /**
     * Revisa si se ha ganado la partida en cada uno de los movimientos.
     *
     * @return Players ID
     */
    protected String checkWin() {
        String player;
        for (int i = 0; i < tablaDeJuego.getColumnCount(); i++) {
            ganador = true;
            player = tablaDeJuegoString[i][0];

            if (!player.equals("")) {
                for (int j = 1; j < tablaDeJuego.getRowCount(); j++) {
                    if (!player.equals(tablaDeJuegoString[i][j])) {
                        ganador = false;
                    }
                }
                if (ganador) {
                    return player;
                }
            }
        }
        for (int j = 0; j < tablaDeJuego.getRowCount(); j++) {
            ganador = true;
            player = tablaDeJuegoString[0][j];

            if (!player.equals("")) {
                for (int i = 1; i < tablaDeJuego.getColumnCount(); i++) {
                    if (!player.equals(tablaDeJuegoString[i][j])) {
                        ganador = false;
                    }
                }
                if (ganador) {
                    return player;
                }
            }
        }

        if ((Objects.equals(tablaDeJuegoString[0][0], tablaDeJuegoString[1][1]) && Objects.equals(tablaDeJuegoString[1][1], tablaDeJuegoString[2][2])) && !tablaDeJuegoString[0][0].equals("")) {
            return tablaDeJuegoString[0][0];
        } else if ((Objects.equals(tablaDeJuegoString[0][2], tablaDeJuegoString[1][1]) && Objects.equals(tablaDeJuegoString[1][1], tablaDeJuegoString[2][0])) && !tablaDeJuegoString[0][2].equals("")) {
            return tablaDeJuegoString[0][2];
        }

        int blanks = 0;
        for (String[] s : tablaDeJuegoString) {
            if (!Arrays.stream(s).toList().contains("")) blanks++;
        }

        if (blanks == 3) return "noBlanks";
        return null;

    }


    /**
     * Cerrar ventana
     */
    @FXML
    private void onClickCloseBtn(){
        TresEnRayaAplicacion.principalStage.close();
    }

    /**
     * Submit stats updated into the file.
     * @param playerName Player Code (X or O)
     * @param stat Stats to Increment
     */
    @FXML
    protected void submitStats(String playerName, String stat) {
        if (!Objects.equals(playerName, "")) {
            if (statsList.get(playerName) != null) {
                switch (stat) {
                    case "win" -> statsList.get(playerName).plusWin();
                    case "lose" -> statsList.get(playerName).plusLoses();
                    case "tie" -> statsList.get(playerName).plusTied();
                }
            } else {
                switch (stat) {
                    case "win" -> statsList.put(playerName, new Estadisticas(playerName, 1, 0, 0));
                    case "lose" -> statsList.put(playerName, new Estadisticas(playerName, 0, 1, 0));
                    case "tie" -> statsList.put(playerName, new Estadisticas(playerName, 0, 0, 1));
                }
            }
            ArrayList<String[]> list = new ArrayList<>();
            for (Estadisticas stats : statsList.values()) {
                String[] statsPlayer = new String[4];
                statsPlayer[0] = stats.getNombre();
                statsPlayer[1] = stats.getVictoria() + "";
                statsPlayer[2] = stats.getDerrota() + "";
                statsPlayer[3] = stats.getEmpate() + "";
                list.add(statsPlayer);
            }
            try {
                FicheroCSV.writeToCSV(list, "src/main/resources/data/stats.csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}