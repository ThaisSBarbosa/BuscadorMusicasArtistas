package br.com.thais.screensound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.thais.screensound.principal.Principal;
import br.com.thais.screensound.repository.ArtistaRepository;

@SpringBootApplication
public class ScreensoundApplication implements CommandLineRunner{
	
	@Autowired
	private ArtistaRepository repositorio;
	
	public static void main(String[] args) {
		SpringApplication.run(ScreensoundApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}
