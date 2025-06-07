package com.alura.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;



public class ConsultaGemini {

    public static String obtenerTraduccion(String texto) {

        String apiCode =  ConfigLoader.getProperty("api_key");
        String modelo = "gemini-2.0-flash";
        String prompt = "Traduce el siguiente texto al espanol: " + texto;


        Client cliente = new Client.Builder().apiKey(apiCode).build();
        try {
            GenerateContentResponse respuesta = cliente.models.generateContent(
                    modelo,
                    prompt,
                    null // Parámetro para configuraciones adicionales
            );

            if (!respuesta.text().isEmpty()) {
                return respuesta.text();
            }
        } catch (Exception e) {
            System.err.println("Error al llamar a la API de Gemini para traducción: " + e.getMessage());
        }

        return null;


    }

}
