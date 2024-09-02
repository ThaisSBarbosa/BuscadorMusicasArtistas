package br.com.thais.screensound.model;

public enum TipoArtista {
	SOLO ("solo"),
	DUPLA ("dupla"),
	BANDA ("banda");
	
	private String tipoTexto;
	
	TipoArtista(String tipoTexto){
		this.tipoTexto = tipoTexto;
	}
	
	public static TipoArtista fromText (String text) {
		for (TipoArtista tipo : TipoArtista.values()) {
			if (tipo.tipoTexto.equalsIgnoreCase(text)) {
				return tipo;
			}
		}
		throw new IllegalArgumentException("Nenhum tipo encontrado para a string fornecida: " + text);
	}
}
