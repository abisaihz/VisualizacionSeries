package com.alura.screenmatch.service;

import com.google.genai.Client;

import java.sql.Struct;

public class ConsultaGemini {
    public static String obtenerTraduccion(String texto) {
        String modelo = "gemini-2.0-flash";
        String prompt = "Traduce el siguiente texto al espanol: " + texto;

        Client cliente = new Client.Builder().apiKey().build();

    }

}
