package io.github.darioteles.servicos;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.darioteles.exceptions.NaoPodeDividirPorZeroException;

/**
 * Define um teste da classe Calculadora. Foi utilizada para exemplificar o uso
 * de TDD.
 */
@RunWith(ParallelRunner.class) // Indica que os testes serao executados a partir da classe ParallelRunner.
public class CalculadoraTest {

	private Calculadora calc;

	@Before
	public void setup() {
		calc = new Calculadora();
		System.out.println("Iniciando...");
	}

	@After
	public void tearDown() {
		System.out.println("Finalizando...");
	}

	@Test
	public void deveSomarDoisValores() {
		// Cenario
		int a = 5;
		int b = 3;

		// Acao
		int resultado = calc.somar(a, b);

		// Verificacao
		Assert.assertEquals(8, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		// Cenario
		int a = 5;
		int b = 3;

		// Acao
		int resultado = calc.subtrair(a, b);

		// Verificacao
		Assert.assertEquals(2, resultado);
	}

	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// Cenario
		int a = 6;
		int b = 3;

		// Acao
		int resultado = calc.dividir(a, b);

		// Verificacao
		Assert.assertEquals(2, resultado);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoDivisaoPorZero() throws NaoPodeDividirPorZeroException {
		// Cenario
		int a = 6;
		int b = 0;

		// Ação
		int resultado = calc.dividir(a, b);

		// Verificacao
		Assert.assertEquals(2, resultado);
	}
}