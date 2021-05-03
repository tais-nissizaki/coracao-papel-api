package br.com.tcon.coracaopapel.negocio.carrinho;

import java.util.List;

import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class PreencherClienteCarrinhoStrategy implements IStrategy {
	
	private ClienteDAO clienteDAO;
	
	public PreencherClienteCarrinhoStrategy(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		if(carrinho != null && carrinho.getCliente() != null && carrinho.getCliente().getId() != null) {
			List<EntidadeDominio> clientes = clienteDAO.consultar(carrinho.getCliente());
			if(clientes != null && !clientes.isEmpty()) {
				carrinho.setCliente((Cliente) clientes.get(0));
			}
		}
		return null;
	}

}
