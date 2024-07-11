package br.com.citrinstar.screenmusic.model;

public enum TipoAlbum {

    ALBUM("Album"),
    SINGLE("Single");

    private String tipo;

    TipoAlbum(String tipo){
        this.tipo = tipo;
    }

    public static TipoAlbum fromString(String text){
        for(TipoAlbum tipo : TipoAlbum.values()){
            if(tipo.tipo.equalsIgnoreCase(text)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de album encontrado!");
    }
}
