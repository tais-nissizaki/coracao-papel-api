package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarSenhaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		if(entidade == null || entidade.getId() == null || entidade.getId().intValue() < 1) {
			retorno.append("Não foi informado o identificador do cliente");	
		}
		return retorno.toString();
	}

}
