package br.com.citrinstar.screenmusic.repository;

import br.com.citrinstar.screenmusic.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {

}
