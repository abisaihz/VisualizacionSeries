package com.alura.screenmatch.service;

import com.alura.screenmatch.dto.EpisodioDTO;
import com.alura.screenmatch.dto.SerieDTO;
import com.alura.screenmatch.model.Categoria;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.model.Serie;
import com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;
    public List<SerieDTO> obtenerTodasLasSeries() {
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getGenero(), s.getActores(),
                    s.getPoster(), s.getSinopsis());
        }
        return null;
    }

    public List<SerieDTO> convierteDatos(List<Serie> serie){
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getGenero(), s.getActores(),
                        s.getPoster(), s.getSinopsis()))
                .collect(Collectors.toList());

    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e-> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTempordadPorNumero(Long id, Long numeroTemporada) {
     return  repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream()
             .map(e->new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
             .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriePorCategoria(String nombreCategoria) {
        return convierteDatos(repository.findByGenero(Categoria.fromEspanol(nombreCategoria)));
    }
}
