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

import io.github.darioteles.entidades.Filme;
import io.github.darioteles.entidades.Locacao;
import io.github.darioteles.entidades.Usuario;
import io.github.darioteles.exceptions.FilmeSemEstoqueException;
import io.github.darioteles.exceptions.LocadoraException;


/**
 * Exemplificação do uso do @Parameters.
 */
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	private LocacaoService service;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String caso;
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1", 1, 100.00);
	private static Filme filme2 = new Filme("Filme 2", 1, 100.00);
	private static Filme filme3 = new Filme("Filme 3", 1, 100.00);
	private static Filme filme4 = new Filme("Filme 4", 1, 100.00);
	private static Filme filme5 = new Filme("Filme 5", 1, 100.00);
	private static Filme filme6 = new Filme("Filme 6", 1, 100.00);
	private static Filme filme7 = new Filme("Filme 6", 1, 100.00);
	
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
		//Cenário
		Usuario usuario = new Usuario("Usuário 1");

		//Ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//Verificação 100+100+75+
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(valorLocacao));
	}
}
