package br.com.tcon.coracaopapel.negocio.usuario;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarConfirmacaoSenhaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		if (!usuario.getSenha().equals(usuario.getConfirmacaoSenha())) {
			return "A Senha e a Confirmação de senha não conferem";
		}
		return null;
	}

}
