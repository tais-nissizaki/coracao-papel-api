package br.com.tcon.coracaopapel.negocio.endereco;

import java.util.List;

import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirClienteEnderecoStrategy implements IStrategy {
	
	private ClienteDAO clienteDAO;

	public AtribuirClienteEnderecoStrategy(ClienteDAO clienteDAO) {
		super();
		this.clienteDAO = clienteDAO;
	}


	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		if(endereco.getCliente() != null && endereco.getCliente().getId() != null && endereco.getCliente().getId() > 0) {
			List<EntidadeDominio> clientes = clienteDAO.consultar(endereco.getCliente());
			endereco.setCliente((Cliente)clientes.get(0));
		}
		return null;
	}

}
