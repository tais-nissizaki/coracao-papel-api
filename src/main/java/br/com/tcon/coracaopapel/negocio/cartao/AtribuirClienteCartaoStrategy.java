package br.com.tcon.coracaopapel.negocio.cartao;

import java.util.List;

import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirClienteCartaoStrategy implements IStrategy {
	
	private ClienteDAO clienteDAO;

	public AtribuirClienteCartaoStrategy(ClienteDAO clienteDAO) {
		super();
		this.clienteDAO = clienteDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Cartao cartao = (Cartao) entidade;
		if(cartao.getCliente() != null && cartao.getCliente().getId() != null && cartao.getCliente().getId() > 0) {
			List<EntidadeDominio> clientes = clienteDAO.consultar(cartao.getCliente());
			cartao.setCliente((Cliente)clientes.get(0));
		}
		return null;
	}

}
