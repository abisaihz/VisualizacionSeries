package com.alura.screenmatch.model;

import com.alura.screenmatch.service.ConsultaGemini;
import jakarta.persistence.*;


import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name= "series")

public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String actores;
    private String poster;
    private String sinopsis;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    public Serie(){}

    public Serie(DatosSerie datosSeries){
        this.titulo = datosSeries.titulo();
        this.totalDeTemporadas = datosSeries.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSeries.evaluacion())).orElse(0);
        this.poster = datosSeries.poster();
        //this.sinopsis = ConsultaGemini.obtenerTraduccion(datosSeries.sinopsis());
        this.sinopsis = datosSeries.sinopsis();
        this.genero = Categoria.fromString(datosSeries.genero().split(",")[0]);
        this.actores = datosSeries.actores();

    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
                ", sinopsis='" + sinopsis + '\'' +
                ", episodios='" + episodios;
    }
}
