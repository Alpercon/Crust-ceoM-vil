package com.example.epsilon.arduino;

public class CardModelo {

    private String titulo, descripcion,boton;
    private int fotoAuto;


    public CardModelo() {
    }

    public CardModelo(String titulo, String descripcion, String boton, int fotoAuto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.boton = boton;
        this.fotoAuto = fotoAuto;
    }


    //getters ys etters


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBoton() {
        return boton;
    }

    public void setBoton(String boton) {
        this.boton = boton;
    }

    public int getFotoAuto() {
        return fotoAuto;
    }

    public void setFotoAuto(int fotoAuto) {
        this.fotoAuto = fotoAuto;
    }
}
