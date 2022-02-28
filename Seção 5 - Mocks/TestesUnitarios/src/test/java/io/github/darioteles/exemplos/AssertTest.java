package io.github.darioteles.exemplos;
import org.junit.Assert;
import org.junit.Test;

import io.github.darioteles.entidades.Usuario;

/**
 * Define uma classe de teste para exemplificar os tipos de Asserts.
 */
public class AssertTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de comparacao", 1, 2); //Parametros: (texto do erro, valor experado, valor)
		Assert.assertEquals(0.51234, 0.512, 0.001); //Parametros: (valor esperado, valor, margem de erro)
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2); //Diferencia tipos primitivos para wrappers
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usuario 1"); 
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2); //Requer que a classe tenha o metodo equals caso contrário será comparado a instancia
		
		Assert.assertSame(u2, u2); //Compara a instancia
		Assert.assertNotSame(u1, u2);
		
		Assert.assertNull(u3);
		Assert.assertNotNull(u2);
	}
	
}
