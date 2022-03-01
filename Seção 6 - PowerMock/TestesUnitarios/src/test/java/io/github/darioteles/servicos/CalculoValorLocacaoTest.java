package io.github.darioteles.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.darioteles.builders.FilmeBuilder;
import io.github.darioteles.daos.LocacaoDAO;
import io.github.darioteles.entidades.Filme;
import io.github.darioteles.entidades.Locacao;
import io.github.darioteles.entidades.Usuario;
import io.github.darioteles.exceptions.FilmeSemEstoqueException;
import io.github.darioteles.exceptions.LocadoraException;


/**
 * Exemplo do uso do @Parameters.
 */
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spc;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String caso;
	
	@Before
	public void setup() {
		
		MockitoAnnotations.initMocks(this);
		
		/* As linhas abaixo foram refatoras após o uso de Annotations do Mockito: */
		//service = new LocacaoService();
		////LocacaoDAO dao = new LocacaoDaoFake(); Refatorado para utilizar Mock em vez de Object Fake.
		//		
		//dao = Mockito.mock(LocacaoDAO.class);
		//service.setLocacaoDAO(dao); //Injeção de dependência.
		//spc = Mockito.mock(SPCService.class);
		//service.setSpcService(spc);
	}
	
	private static Filme filme1 = FilmeBuilder.umFilme().agora();
	private static Filme filme2 = FilmeBuilder.umFilme().agora();
	private static Filme filme3 = FilmeBuilder.umFilme().agora();
	private static Filme filme4 = FilmeBuilder.umFilme().agora();
	private static Filme filme5 = FilmeBuilder.umFilme().agora();
	private static Filme filme6 = FilmeBuilder.umFilme().agora();
	private static Filme filme7 = FilmeBuilder.umFilme().agora();
	
	@Parameters(name="Teste - {2}")
	public static Collection<Object[]> getParameros(){
		return Arrays.asList(new Object[][]{
			{Arrays.asList(filme1, filme2), 200.00, "2º Filme 0%"},
			{Arrays.asList(filme1, filme2, filme3), 275.00, "3º Filme 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 325.00, "4º Filme 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 350.00, "5º Filme 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 350.00, "6º Filmes 100"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 450.00, "7º Filmes 100"}
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//Cenario
		Usuario usuario = new Usuario("Usuário 1");

		//Acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//Verificacao 100+100+75
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(valorLocacao));
	}
}
