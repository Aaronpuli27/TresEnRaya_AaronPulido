module com.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires org.jetbrains.annotations;


    opens com.example.tres_en_raya to javafx.fxml;
    exports com.example.tres_en_raya;
    exports com.example.tres_en_raya.controller;
    opens com.example.tres_en_raya.controller to javafx.fxml;
}