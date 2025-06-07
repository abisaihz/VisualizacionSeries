package com.alura.screenmatch.model;

import com.alura.screenmatch.service.ConsultaGemini;


import java.util.OptionalDouble;

public class Serie {

    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private Categoria genero;
    private String actores;
    private String poster;
    private String sinopsis;


    public Serie(DatosSerie datosSeries){
        this.titulo = datosSeries.titulo();
        this.totalDeTemporadas = datosSeries.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSeries.evaluacion())).orElse(0);
        this.poster = datosSeries.poster();
        this.sinopsis = ConsultaGemini.obtenerTraduccion(datosSeries.sinopsis());
        //this.sinopsis = datosSeries.sinopsis();
        this.genero = Categoria.fromString(datosSeries.genero().split(",")[0]);
        this.actores = datosSeries.actores();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return  "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", evaluacion=" + evaluacion +
                ", genero=" + genero +
                ", actores='" + actores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopsis='" + sinopsis;
    }
}
