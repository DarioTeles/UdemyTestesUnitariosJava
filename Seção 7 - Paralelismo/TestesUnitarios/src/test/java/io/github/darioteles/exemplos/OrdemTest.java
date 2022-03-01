package io.github.darioteles.exemplos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Define uma classe de teste para exemplificar testes dependentes entre si e
 * como definir uma ordem.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

	public static int count = 0;

	@Test
	public void inicial() {
		count = 1;
	}

	@Test
	public void verificar() {
		Assert.assertEquals(1, count);
	}
}
