package io.github.darioteles.entidades;

import java.util.Objects;

/**
 * Define um filme.
 */
public class Filme {

	private String nome;
	private Integer estoque;
	private Double precoLocacao;

	public Filme() {
	}

	public Filme(String nome, Integer estoque, Double precoLocacao) {
		this.nome = nome;
		this.estoque = estoque;
		this.precoLocacao = precoLocacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getEstoque() {
		return estoque;
	}

	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}

	public Double getPrecoLocacao() {
		return precoLocacao;
	}

	public void setPrecoLocacao(Double precoLocacao) {
		this.precoLocacao = precoLocacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estoque, nome, precoLocacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filme other = (Filme) obj;
		return Objects.equals(estoque, other.estoque) && Objects.equals(nome, other.nome)
				&& Objects.equals(precoLocacao, other.precoLocacao);
	}

}