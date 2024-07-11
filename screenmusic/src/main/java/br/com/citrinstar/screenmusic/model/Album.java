package br.com.citrinstar.screenmusic.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Album {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Musica> musicas;
    @Enumerated(EnumType.STRING)
    private TipoAlbum tipo;

    Album(){}

    Album(String nome, Artista artista, String tipo){
        this.nome = nome;
        this.artista = artista;
        this.tipo = TipoAlbum.fromString(tipo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public void addMusica(Musica musica){
        musicas.add(musica);
        musica.setAlbum(this);
    }

    public TipoAlbum getTipo() {
        return tipo;
    }

    public void setTipo(TipoAlbum tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
