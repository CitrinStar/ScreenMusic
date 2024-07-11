package br.com.citrinstar.screenmusic.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Artista {

    private String nome;
    @JsonAlias("extract")
    private String sobre;
    private Genero genero;
    private List<Album> albuns;

}
