package br.com.tcon.coracaopapel.negocio.usuario;

import java.util.List;

import br.com.tcon.coracaopapel.Encriptador;
import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarSenhaAtualStrategy implements IStrategy {
	
	private ClienteDAO clienteDAO;
	
	public ValidarSenhaAtualStrategy(ClienteDAO clienteDAO) {
		super();
		this.clienteDAO = clienteDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		List<EntidadeDominio> consultarClientes = clienteDAO.consultar(usuario.getCliente());
		if(consultarClientes == null || consultarClientes.isEmpty()) {
			return "Senha atual não confere.";
		} else if (consultarClientes.size() > 1) {
			return "Senha atual não confere.";
		}
		Cliente clienteBD = (Cliente)consultarClientes.get(0);
		if (!Encriptador.passwordEncoder.matches(usuario.getSenhaAntiga(), clienteBD.getUsuario().getSenha())) {
			return "Senha atual não confere.";
		}
		usuario.setId(clienteBD.getUsuario().getId());
		return null;
	}

}
