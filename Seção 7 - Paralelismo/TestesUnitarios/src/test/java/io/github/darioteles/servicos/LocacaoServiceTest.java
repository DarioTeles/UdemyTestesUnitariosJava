package io.github.darioteles.servicos;

import static io.github.darioteles.matchers.MatchersProprios.eHoje;
import static io.github.darioteles.matchers.MatchersProprios.eHojeComDiferecialDeDias;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.github.darioteles.builders.FilmeBuilder;
import io.github.darioteles.builders.LocacaoBuilder;
import io.github.darioteles.builders.UsuarioBuilder;
import io.github.darioteles.daos.LocacaoDAO;
import io.github.darioteles.entidades.Filme;
import io.github.darioteles.entidades.Locacao;
import io.github.darioteles.entidades.Usuario;
import io.github.darioteles.exceptions.FilmeSemEstoqueException;
import io.github.darioteles.exceptions.LocadoraException;
import io.github.darioteles.matchers.MatchersProprios;
import io.github.darioteles.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LocacaoService.class)
public class LocacaoServiceTest {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private LocacaoDAO dao;
	@Mock
	private SPCService spc;
	@Mock
	private EmailService emailService;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before // JUnit zera as variaveis em cada teste exceto se a variavel for estática. Isso
			// permite independencia entre os testes.
	public void setup() {
		MockitoAnnotations.initMocks(this);

		/* As linhas abaixo foram refatoradas após o uso de Annotations do Mockito */
		// service = new LocacaoService();
		// LocacaoDAO dao = new LocacaoDaoFake(); Exemplo de Fake Object (Implementação
		// LocacaoDaoFake). Refatorado para utilizar Mock em vez de Object Fake.
		//
		// dao = Mockito.mock(LocacaoDAO.class);
		// service.setLocacaoDAO(dao); //Injeção de dependência.
		// spc = Mockito.mock(SPCService.class);
		// service.setSpcService(spc);
		// emailService = Mockito.mock(EmailService.class);
		// service.setEmailService(emailService);

	}

	@After
	public void tearDown() {
		System.out.println("Fim de caso de teste (LocacaoServiceTest).");
	}

	@Test // Annotation JUnit que especifica um teste.
	public void deveAlugarFilme() throws Exception {

		/*
		 * Para ser considerado um teste e necessário que o metodo atenda os princípios
		 * FIRST: Fast, Isolated/Independent, Repeatable, Self-validating e thorough. O
		 * ideal e ter um método de teste para cada verificação Assert.
		 */

		/* Assume define uma situacao para ser realizado o teste */
		// Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(),
		// Calendar.SATURDAY)); //Testar dias diferentes de sábado //refatorado ao
		// utilizar PowerMock para mockar construtor

		/* Ciclo de vida de um teste: Cenario, Acao e Verificacao: */

		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(100.00).agora());

		// Acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificacao
		Assert.assertEquals(100.00, locacao.getValor(), 0.01); // Assert define um ponto logico
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(100.00));
		// Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new
		// Date()), CoreMatchers.is(true)); Refatorado para utilizar Matcher proprio.
		Assert.assertThat(locacao.getDataLocacao(), eHoje());
		// Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(),
		// DataUtils.obterDataComDiferencaDias(1))); Refatorado para utilizar Matcher
		// proprio.
		Assert.assertThat(locacao.getDataRetorno(), eHojeComDiferecialDeDias(1));

	}

	/* Formas de tratar excecoes: */

	/*
	 * 1º Forma de especificar que um teste tera uma exceção lançada. Funciona bem
	 * para excecoes bem definidas.
	 */
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());

		// Acao
		service.alugarFilme(usuario, filmes);
	}

	/*
	 * 2º Forma de especificar que um teste tera uma excecao lancada. Permite mais
	 * controle.
	 */
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// Cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		// Acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("Deveria ter lançado uma excecao de usuario vazio");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
		}
	}

	/* 3º Forma de especificar que um teste tera uma exceção lancada. */
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueException {
		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// Acao
		service.alugarFilme(usuario, null);
	}

	/*
	 * Para exemplificar o uso de parameters, foi criada uma classe de teste chamada
	 * CalculoValorLocacao responsavel por aplicar os testes referente ao calculo de
	 * valor total e seu devidos descontos. Essa classe refatorou os testes abaixo:
	 */

//	@Test
//	public void testeDevePagar75Filme3() throws FilmeSemEstoqueException, LocadoraException {
//		//Cenario
//		Usuario usuario = new Usuario("Usuário 1");
//		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 100.00), new Filme("Filme 2", 1, 100.00), new Filme("Filme 3", 1, 100.00));
//
//		//Acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//		
//		//Verificacao 100 + 100 + 75
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(275.00));
//	}
//	
//	@Test
//	public void testeDevePagar50Filme4() throws FilmeSemEstoqueException, LocadoraException {
//		//Cenario
//		Usuario usuario = new Usuario("Usuário 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 1, 100.00), 
//				new Filme("Filme 2", 1, 100.00), 
//				new Filme("Filme 3", 1, 100.00),
//				new Filme("Filme 4", 1, 100.00));
//
//		//Acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//		
//		//Verificacao 
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(325.00));
//	}
//	
//	@Test
//	public void testeDevePagar25Filme5() throws FilmeSemEstoqueException, LocadoraException {
//		//Cenario
//		Usuario usuario = new Usuario("Usuário 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 1, 100.00), 
//				new Filme("Filme 2", 1, 100.00), 
//				new Filme("Filme 3", 1, 100.00),
//				new Filme("Filme 4", 1, 100.00),
//				new Filme("Filme 5", 1, 100.00));
//
//		//Acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//		
//		//Verificacao
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(350.00));
//	}
//	
//	@Test
//	public void testeDevePagar0Filme6() throws FilmeSemEstoqueException, LocadoraException {
//		//Cenario
//		Usuario usuario = new Usuario("Usuário 1");
//		List<Filme> filmes = Arrays.asList(
//				new Filme("Filme 1", 1, 100.00), 
//				new Filme("Filme 2", 1, 100.00), 
//				new Filme("Filme 3", 1, 100.00),
//				new Filme("Filme 4", 1, 100.00),
//				new Filme("Filme 5", 1, 100.00),
//				new Filme("Filme 6", 1, 100.00));
//
//		//Acao
//		Locacao locacao = service.alugarFilme(usuario, filmes);
//		
//		//Verificacao
//		Assert.assertThat(locacao.getValor(), CoreMatchers.is(350.00));
//	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {

		// Cenario
		// Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),
		// Calendar.SATURDAY)); //Testar somente vendas ao sábado //Refatorado ao
		// utilizar PowerMock para mockar construtor.
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017)); // Mockando
																											// construtor
																											// da classe
																											// Date.

		// Acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificao
		// boolean eSegunda = DataUtils.verificarDiaSemana(locacao.getDataRetorno(),
		// Calendar.MONDAY);
		// Assert.assertTrue(eSegunda); Refatorado para utilizar Matcher proprio.

		// Assert.assertThat(locacao.getDataRetorno(),
		// MatchersProprios.caiEm(Calendar.MONDAY));
		Assert.assertThat(locacao.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}

	@Test
	public void naoDeveAlugarFilmeNegativadoSPC() throws Exception {

		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		Mockito.when(spc.possuiNegativacao(usuario)).thenReturn(true); // Expectativa

		// Acao
		try {
			service.alugarFilme(usuario, filmes);

			// Verificacao
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuário Negativado."));
		}

		// Verificacao
		Mockito.verify(spc).possuiNegativacao(usuario);
	}

	@Test
	public void deveEnviarEmailParaAlocacoesAtrasadas() {
		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario em dia").agora();
		Usuario usuario3 = UsuarioBuilder.umUsuario().comNome("Usuario Atraso").agora();
		List<Locacao> locacoes = Arrays.asList(
				LocacaoBuilder.umLocacao().comUsuario(usuario).comDataRetorno(DataUtils.obterDataComDiferencaDias(-2))
						.agora(),
				LocacaoBuilder.umLocacao().comUsuario(usuario2).agora(), LocacaoBuilder.umLocacao().comUsuario(usuario3)
						.comDataRetorno(DataUtils.obterDataComDiferencaDias(-2)).agora());
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoes); // Define uma expectativa

		// Acao
		service.notificarAtrasos();

		// Verificacao
		Mockito.verify(emailService).notificarAtraso(usuario); // Verifica comportamentos
		Mockito.verify(emailService, Mockito.times(1)).notificarAtraso(usuario3);
		Mockito.verify(emailService, Mockito.never()).notificarAtraso(usuario2);

		Mockito.verify(emailService, Mockito.times(2)).notificarAtraso(Mockito.any(Usuario.class)); // Forma mais
																									// genérica

		Mockito.verifyNoMoreInteractions(emailService);

	}

	@Test
	public void deveTratarErroSPC() throws Exception {
		// Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		Mockito.when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha catastrófica."));

		// Verificacao
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente.");

		// Acao
		service.alugarFilme(usuario, filmes);

	}

	@Test
	public void deveProrrogarUmaLocacao() {
		// Cenario
		Locacao locacao = LocacaoBuilder.umLocacao().agora();

		// Acao
		service.prorrogaLocacao(locacao, 3);

		// Verificacao
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();
		Assert.assertThat(locacaoRetornada.getValor(), CoreMatchers.is(12.00));
		Assert.assertThat(locacaoRetornada.getDataLocacao(), MatchersProprios.eHoje());
		Assert.assertThat(locacaoRetornada.getDataRetorno(), MatchersProprios.eHojeComDiferecialDeDias(3));

	}
}
