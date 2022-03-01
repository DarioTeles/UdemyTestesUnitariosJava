package io.github.darioteles.servicos;

import static io.github.darioteles.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.github.darioteles.daos.LocacaoDAO;
import io.github.darioteles.entidades.Filme;
import io.github.darioteles.entidades.Locacao;
import io.github.darioteles.entidades.Usuario;
import io.github.darioteles.exceptions.FilmeSemEstoqueException;
import io.github.darioteles.exceptions.LocadoraException;
import io.github.darioteles.utils.DataUtils;

/**
 * Define uma classe de serviço para locacao de filme.
 */
public class LocacaoService {

	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		boolean negativado;

		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente.");
		}

		if (negativado) {
			throw new LocadoraException("Usuário Negativado.");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		// Calculo para o valor total
		Double valorTotal = 0d;
		for (int i = 0; i < filmes.size(); i++) {
			Double valorFilme = filmes.get(i).getPrecoLocacao();
			switch (i) {
			case 2:
				valorFilme *= 0.75;
				break;
			case 3:
				valorFilme *= 0.50;
				break;
			case 4:
				valorFilme *= 0.25;
				break;
			case 5:
				valorFilme *= 0.0;
				break;
			}
			valorTotal += valorFilme;
		}

		locacao.setValor(valorTotal);

		// Entregar no dia seguinte exceto se data de alocacao seja sabado
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		dao.salvar(locacao);

		return locacao;
	}

	/* As linhas abaixo foram refatoradas após o uso de Annotations do Mockito: */

	//// Injeção de dependência
	// public void setLocacaoDAO(LocacaoDAO dao) {
	// this.dao = dao;
	// }
	//
	//// Injeção de dependência
	// public void setSpcService(SPCService spcService) {
	// this.spcService = spcService;
	// }
	//
	//// Injeção de dependência
	// public void setEmailService(EmailService emailService) {
	// this.emailService = emailService;
	// }

	public void notificarAtrasos() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();

		for (Locacao locacao : locacoes) {
			if (locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}

	public void prorrogaLocacao(Locacao locacao, int dias) {
		Locacao novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		novaLocacao.setValor(locacao.getValor() * dias);
		dao.salvar(novaLocacao);
	}

}