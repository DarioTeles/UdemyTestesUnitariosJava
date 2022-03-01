package io.github.darioteles.servicos;

import io.github.darioteles.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);
	
}
