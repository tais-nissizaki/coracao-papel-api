package br.com.tcon.coracaopapel.negocio;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

public interface IStrategy {
	public String processar(EntidadeDominio entidade);
}
