package com.alura.screenmatch.principal;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.alura.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.model.DatosSerie;
import com.alura.screenmatch.model.DatosTemporada;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.service.ConvierteDatos;
import com.fasterxml.jackson.databind.ser.SerializerCache;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=acba02d5";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraMenu(){
        System.out.println("Por favor escribe el nombre de la serie que quieras buscar");
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

//        temporadas.forEach(t -> t.episodios()
//                .forEach(e -> System.out.println(e.titulo())));

        // Convertir todas las informaciones a una lista del tipo dato episodio
        System.out.println("Top 5 Episodios");

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // top 5 episodios
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Filtro NA " + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Sorted " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Upper " + e))
                .limit(5)
//                .peek(e -> System.out.println("Limite " + e))
                .forEach(System.out::println);




        // Convirtiendo los datos a un tipo episodio

        //System.out.println("Esta es la impresion de clase");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);

        //Busqueda de episodios a partir de X anio
//        System.out.println("Por favor indica el anio a partir del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();

//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1,1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> {
//                    System.out.println("Temporada: " + e.getTemporada() +
//                            " Episodio: " + e.getTitulo() +
//                            " Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(dtf));
//
//                });

        // Busca episodios por pedazos de titulo
//        System.out.println("Por favor escriba el titulo del episodio que desea ver");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findAny();
//
//        if (episodioBuscado.isPresent()){
//            System.out.println(" Episodio encontrado");
//            System.out.println(" Los datos son: "+ episodioBuscado.get());
//        } else {
//            System.out.println(" Episodio no encontrado");
//        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() >  0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() >0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("media de las Evaluaciones: "+ est.getAverage());
        System.out.println("Episodio mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: "+ est.getMin());


    }


}
