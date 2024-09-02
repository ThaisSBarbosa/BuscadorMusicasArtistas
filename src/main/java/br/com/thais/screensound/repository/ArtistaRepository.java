package br.com.thais.screensound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.thais.screensound.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long>{

	Optional<Artista> findByNomeContainingIgnoreCase(String nomeArtista);
}
