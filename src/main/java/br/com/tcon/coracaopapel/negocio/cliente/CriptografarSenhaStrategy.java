package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.Encriptador;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CriptografarSenhaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		if(cliente.getUsuario() != null && cliente.getUsuario().getSenha() != null) {
			cliente.getUsuario().setSenha(Encriptador.passwordEncoder.encode(cliente.getUsuario().getSenha()));
		}
		return null;
	}

}
