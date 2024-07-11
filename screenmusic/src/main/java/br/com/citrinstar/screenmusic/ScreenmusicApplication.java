package br.com.citrinstar.screenmusic;

import br.com.citrinstar.screenmusic.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmusicApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmusicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal();
		menu.exibeMenu();
	}
}
