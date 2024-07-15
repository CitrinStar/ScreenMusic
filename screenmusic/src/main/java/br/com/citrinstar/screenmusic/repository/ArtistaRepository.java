package br.com.citrinstar.screenmusic.repository;

import br.com.citrinstar.screenmusic.model.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    @Query("SELECT a FROM Artista a WHERE a.nome ILIKE %:nome%")
    Optional<Artista> buscarArtistaPeloNome(String nome);
}
