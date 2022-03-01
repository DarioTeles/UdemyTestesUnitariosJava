package io.github.darioteles.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.github.darioteles.servicos.CalculadoraTest;
import io.github.darioteles.servicos.CalculoValorLocacaoTest;
import io.github.darioteles.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ CalculadoraTest.class, CalculoValorLocacaoTest.class, LocacaoServiceTest.class })
public class SuiteExecution {

	@BeforeClass // Método a ser executado no inicio da classe.
	public static void setupClass() {
		System.out.println("Before");
	}

	@AfterClass // Método a ser executado no final da classe.
	public static void tearDownClass() {
		System.out.println("After");
	}
}
