package br.com.thais.screensound.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "musicas")
public class Musica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String titulo;
	@ManyToOne
	private Artista artista = new Artista();
	
	public Musica() {
	}
	public Musica(String titulo, Artista artista) {
		this.titulo = titulo;
		this.artista = artista;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Artista getArtista() {
		return artista;
	}
	public void setArtista(Artista artista) {
		this.artista = artista;
	}
	@Override
	public String toString() {
		return "[Música=" + titulo + ", Artista=" + artista.getNome() + "]";
	}
}
