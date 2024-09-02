package br.com.thais.screensound.principal;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.thais.screensound.model.Artista;
import br.com.thais.screensound.model.Musica;
import br.com.thais.screensound.model.TipoArtista;
import br.com.thais.screensound.repository.ArtistaRepository;
import br.com.thais.screensound.service.ConsultaChatGPT;

public class Principal {

	public Principal(ArtistaRepository repositorio) {
		this.repositorio = repositorio;
	}

	private ArtistaRepository repositorio;

	private Scanner leitura = new Scanner(System.in);

	public void exibeMenu() {
		var opcao = -1;

		while (opcao != 9) {
			try {
				var menu = """
						\n*** Screen Sound Músicas ***

						1 - Cadastrar artistas
						2 - Cadastrar músicas
						3 - Listar músicas
						4 - Buscar músicas por artistas
						5 - Pesquisar dados sobre um artista
						9 - Sair

						""";

				System.out.println(menu);
				opcao = leitura.nextInt();
				leitura.nextLine();

				switch (opcao) {
				case 1:
					cadastrarArtistas();
					break;
				case 2:
					cadastrarMusicas();
					break;
				case 3:
					listarMusicas();
					break;
				case 4:
					buscarMusicasPorArtista();
					break;
				case 5:
					pesquisarDadosDoArtista();
					break;
				case 9:
					System.out.println("Encerrando a aplicação!");
					break;
				default:
					System.out.println("Opção inválida!");
				}
			} catch (Exception e) {
				System.out.println("Ocorreu um erro!");
			}
		}
	}

	private void cadastrarArtistas() {
		System.out.println("Esse artista é de qual tipo? (solo, dupla ou banda)");
		var tipo = leitura.nextLine();
		var tipoArtistaEnum = TipoArtista.fromText(tipo);

		System.out.println("Qual é o nome do artista?");
		var nome = leitura.nextLine();

		var artista = new Artista(nome, tipoArtistaEnum);
		repositorio.save(artista);
		System.out.println("Artista cadastrado.");
	}

	private void cadastrarMusicas() {
		System.out.println("De qual artista é esta música?\n");
		listarArtistas();
		var nomeArtista = leitura.nextLine();
		Optional<Artista> artistaEncontrado = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

		if (artistaEncontrado.isPresent()) {
			System.out.println("Qual é o título da música?");
			var titulo = leitura.nextLine();

			var musicaNova = new Musica(titulo, artistaEncontrado.get());
			var musicasDoArtista = artistaEncontrado.get().getMusicas();
			musicasDoArtista.add(musicaNova);
			artistaEncontrado.get().setMusicas(musicasDoArtista);
			repositorio.save(artistaEncontrado.get());

			System.out.println("Música cadastrada.");
		} else {
			System.out.println("Artista não encontrado.");
		}
	}

	private void listarArtistas() {
		var artistas = repositorio.findAll();
		artistas.stream().sorted(Comparator.comparing(Artista::getNome)).forEach(System.out::println);
	}

	private void listarMusicas() {
		var artistas = repositorio.findAll();

		if (artistas.size() > 0) {
			List<Musica> musicas = artistas.stream()
					.flatMap(a -> a.getMusicas().stream().map(m -> new Musica(m.getTitulo(), a)))
					.collect(Collectors.toList());
			musicas.forEach(System.out::println);
		} else {
			System.out.println("Nenhum artista encontrado.");
		}
	}

	private void buscarMusicasPorArtista() {
		System.out.println("De qual artista deseja pesquisar as músicas?\n");
		listarArtistas();
		var nomeArtista = leitura.nextLine();
		Optional<Artista> artistaEncontrado = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

		if (artistaEncontrado.isPresent()) {
			artistaEncontrado.get().getMusicas().forEach(System.out::println);
		} else {
			System.out.println("Artista não encontrado.");
		}
	}

	private void pesquisarDadosDoArtista() {
		System.out.println("Qual artista deseja pesquisar?\n");
		listarArtistas();
		var nomeArtista = leitura.nextLine();
		Optional<Artista> artistaEncontrado = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

		if (artistaEncontrado.isPresent()) {
			System.out.println(ConsultaChatGPT.obterInfoArtista(artistaEncontrado.get().getNome()));
		} else {
			System.out.println("Artista não encontrado.");
		}
	}
}
