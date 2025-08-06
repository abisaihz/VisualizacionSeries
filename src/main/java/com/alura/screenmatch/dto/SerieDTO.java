package com.alura.screenmatch.dto;

import com.alura.screenmatch.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(
        Long id,
     String titulo,
     Integer totalDeTemporadas,
     Double evaluacion,
    Categoria genero,
    String actores,
    String poster,
    String sinopsis) {
}
