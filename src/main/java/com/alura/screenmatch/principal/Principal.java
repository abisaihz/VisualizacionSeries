package com.alura.screenmatch.principal;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import com.fasterxml.jackson.databind.ser.SerializerCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=acba02d5";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu(){
        System.out.println("Por favor escribe el nombre de la seria que quieras buscar");
        //Busca los datos generales de la serie
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.trim().replace(" ","+") + API_KEY);

        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        System.out.println(datos);

        // Busca los datos de todas las temporadas

        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalDeTemporadas() ; i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ","+").trim() + "&season="+ i +API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
        //temporadas.forEach(System.out::println);


        // Mostrar solo el titulo de los episodios para las temporadas
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporadas.size(); j++) {
//                System.out.println(episodiosTemporadas.get(j).titulo());
//
//            }
//        }

        temporadas.forEach(t -> t.episodios()
                .forEach(e -> System.out.println(e.titulo())));

    }


}
