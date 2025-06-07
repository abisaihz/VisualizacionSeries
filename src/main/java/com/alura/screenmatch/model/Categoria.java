package com.alura.screenmatch.model;

public enum Categoria {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime");

    private String categoriaOmdb;
    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    // Estudiar que son las enum
    //Esta funcion aun no se como se va a utilizar

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }


}
