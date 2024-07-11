package br.com.citrinstar.screenmusic.model;

public enum Genero {
    POP("Pop"),
    ROCK("Rock"),
    PAGODE("Pagode"),
    SERTANEJO("Sertanejo"),
    INDIE("Indie");

    private String tipo;

    Genero(String tipo){
        this.tipo = tipo;
    }

    public static Genero fromString(String text){
        for(Genero genero : Genero.values()){
            if(genero.tipo.equalsIgnoreCase(text)){
                return genero;
            }
        }
        throw new IllegalArgumentException("Nenhum gênero encontrado");
    }

}
