package io.github.darioteles.entidades;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Define uma locacao de filme.
 */
public class Locacao {

	private Usuario usuario;
	private List<Filme> filmes;
	private Date dataLocacao;
	private Date dataRetorno;
	private Double valor;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataLocacao() {
		return dataLocacao;
	}

	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataLocacao, dataRetorno, filmes, usuario, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		return Objects.equals(dataLocacao, other.dataLocacao) && Objects.equals(dataRetorno, other.dataRetorno)
				&& Objects.equals(filmes, other.filmes) && Objects.equals(usuario, other.usuario)
				&& Objects.equals(valor, other.valor);
	}

}