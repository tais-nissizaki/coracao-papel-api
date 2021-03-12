package br.com.tcon.coracaopapel.controle;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

public interface ICommand {

	public Object executar(EntidadeDominio entidade);
	
}
