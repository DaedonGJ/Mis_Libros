package com.example.administrador.mis_libros.modelo;

import android.support.annotation.NonNull;

/**
 * Created by Administrador on 30/11/2017.
 */

public class Libros implements Comparable<Libros> {

    @Override
    public int compareTo(@NonNull Libros o) {
        return this.titulo.compareTo(o.titulo);
    }

    public enum Genero {NARRATIVA, TEATRO, POESIA};

    protected String titulo;
    protected String autor;
    protected String resumen;
    protected Genero genero;

    public Libros(String titulo, String autor, String resumen, Genero genero){
        this.titulo=titulo;
        this.autor=autor;
        this.resumen=resumen;
        this.genero=genero;
    }
    public Libros(String titulo, String autor){
        this(titulo, autor, "", Genero.NARRATIVA);
    }
    public Libros(){
        super();
    }

    @Override
    public String toString() {
        return titulo+", "+autor+", "+resumen+", "+genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
