package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.Encriptador;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CriptografarSenhaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = null;
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			usuario = cliente.getUsuario();
		} else if (entidade instanceof Usuario) {
			usuario = (Usuario) entidade;
		}
		
		if(usuario != null && usuario.getSenha() != null) {
			usuario.setSenha(Encriptador.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		return null;
	}

}
