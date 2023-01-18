package com.example.tres_en_raya.model;

public class Estadisticas {
    private final String nombre;
    private int empate;
    private int victoria;
    private int derrota;

    public Estadisticas(String nombre, int empate, int victoria, int derrota) {
        this.nombre = nombre;
        this.empate = empate;
        this.victoria = victoria;
        this.derrota = derrota;
    }


    public String getNombre() {
        return nombre;
    }

    public void plusWin(){
        this.victoria++;
    }

    public void plusLoses(){
        this.derrota++;
    }

    public void plusTied(){
        this.empate++;
    }

    public int getVictoria() {
        return victoria;
    }

    public int getDerrota() {
        return derrota;
    }

    public int getEmpate() {
        return empate;
    }
}
