package com.example.rolparkuor;

import androidx.annotation.NonNull;

public class DadoColetado {
    private int id;
    private String titulo;
    private float numero;
    private int ranking;

    public DadoColetado(int id, String titulo, float numero, int ranking) {
        this.id = id;
        this.titulo = titulo;
        this.numero = numero;
        this.ranking = ranking;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public float getNumero() {
        return numero;
    }

    public int getRanking() {
        return ranking;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setNumero(float numero) {
        this.numero = numero;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @NonNull
    @Override
    public String toString() {
        return titulo + ": " + Float.toString(numero) + " (" + Integer.toString(ranking) + ")";
    }
}