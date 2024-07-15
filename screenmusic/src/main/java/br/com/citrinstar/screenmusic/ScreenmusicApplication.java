package br.com.citrinstar.screenmusic;

import br.com.citrinstar.screenmusic.principal.Principal;
import br.com.citrinstar.screenmusic.repository.AlbumRepository;
import br.com.citrinstar.screenmusic.repository.ArtistaRepository;
import br.com.citrinstar.screenmusic.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmusicApplication implements CommandLineRunner {

	@Autowired
	private ArtistaRepository repoArtista;
	@Autowired
	private AlbumRepository repoAlbum;
	@Autowired
	private MusicaRepository repoMusica;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmusicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal(repoArtista, repoAlbum, repoMusica);
		menu.exibeMenu();
	}
}
