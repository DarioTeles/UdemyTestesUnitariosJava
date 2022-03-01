package io.github.darioteles.daos;

import java.util.List;

import io.github.darioteles.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();

}
