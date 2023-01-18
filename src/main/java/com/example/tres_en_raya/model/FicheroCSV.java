package com.example.tres_en_raya.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FicheroCSV {

    public static void writeToCSV(ArrayList<String[]> list, @NotNull String path) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(path));
        writer.writeAll(list);
        System.out.println("Importado");
        writer.close();
    }

    public static List<String[]> readCSV(@NotNull String path) throws IOException {
        Reader reader = Files.newBufferedReader(Path.of(path));
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
