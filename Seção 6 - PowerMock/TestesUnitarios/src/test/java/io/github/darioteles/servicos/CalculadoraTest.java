package io.github.darioteles.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.darioteles.exceptions.NaoPodeDividirPorZeroException;

/**
 * Define um teste da classe Calculadora. Foi utilizada para exemplificar o uso de TDD.
 */
public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//Cenario
		int a = 5;
		int b = 3;
		
		//Acao
		int resultado = calc.somar(a, b);
		
		//Verificacao
		Assert.assertEquals(8, resultado);	
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//Cenario
		int a = 5;
		int b = 3;
		
		//Acao
		int resultado = calc.subtrair(a, b);
		
		//Verificacao
		Assert.assertEquals(2, resultado);	
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//Cenario
		int a = 6;
		int b = 3;
		
		//Acao
		int resultado = calc.dividir(a, b);
		
		//Verificacao
		Assert.assertEquals(2, resultado);	
	}
	
	@Test(expected=NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoDivisaoPorZero() throws NaoPodeDividirPorZeroException {
		//Cenario
		int a = 6;
		int b = 0;
		
		//Ação
		int resultado = calc.dividir(a, b);
		
		//Verificacao
		Assert.assertEquals(2, resultado);	
	}

}
