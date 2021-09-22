package br.com.tcon.coracaopapel.negocio.usuario;

import br.com.tcon.coracaopapel.Encriptador;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CriptografarSenhaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		if(usuario.getSenha() != null) {
			usuario.setSenha(Encriptador.passwordEncoder.encode(usuario.getSenha()));
		}
		return null;
	}

}
