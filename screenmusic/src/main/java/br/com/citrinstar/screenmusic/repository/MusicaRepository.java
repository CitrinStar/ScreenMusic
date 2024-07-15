package br.com.citrinstar.screenmusic.repository;

import br.com.citrinstar.screenmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica,Long> {

    @Query("SELECT m FROM Musica m JOIN m.album a JOIN a.artista ar WHERE ar.nome ILIKE %:nomeArtista%")
    List<Musica> buscarMusicasPorArtista(String nomeArtista);

}
