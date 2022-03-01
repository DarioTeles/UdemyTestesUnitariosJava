package io.github.darioteles.builders;

import io.github.darioteles.entidades.Filme;

/**
 * Define um construtor de Filme.
 */
public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setNome("Filme 1");
		builder.filme.setEstoque(2);
		builder.filme.setPrecoLocacao(100.00);
		return builder;
	}
	
	public FilmeBuilder semEstoque() {
		filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder comValor (Double valor) {
		filme.setPrecoLocacao(valor);
		return this;
	}
	
	public Filme agora() {
		return filme;
	}

}
