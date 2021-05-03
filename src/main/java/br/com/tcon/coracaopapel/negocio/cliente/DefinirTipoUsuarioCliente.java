package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.ArrayList;
import java.util.List;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Permissao;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirTipoUsuarioCliente implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			if(cliente.getUsuario() != null) {
				Permissao permissao = new Permissao();
				permissao.setId(2);
				List<Permissao> permissoes = new ArrayList<>();
				permissoes.add(permissao);
				cliente.getUsuario().setPermissoes(permissoes);
			}
		}
		return null;
	}

}
