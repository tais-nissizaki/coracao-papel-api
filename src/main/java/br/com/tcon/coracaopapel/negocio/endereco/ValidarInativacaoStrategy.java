package br.com.tcon.coracaopapel.negocio.endereco;

import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.dao.EnderecoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarInativacaoStrategy implements IStrategy {
	
	private EnderecoDAO enderecoDAO;	
	private ClienteDAO clienteDAO;
	
	public ValidarInativacaoStrategy(ClienteDAO clienteDAO, EnderecoDAO enderecoDAO) {
		super();
		this.clienteDAO = clienteDAO;
		this.enderecoDAO = enderecoDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		Endereco enderecoBD = (Endereco) enderecoDAO.consultar(endereco).get(0);
		Cliente clienteBD = (Cliente) clienteDAO.consultar(endereco.getCliente()).get(0);
		TipoEndereco tipoEnderecoBD = enderecoBD.getTipoEndereco();
		int countTipoEndereco = 0;
		for (Endereco enderecoCliente: clienteBD.getEnderecos()) {
			if(enderecoCliente.getTipoEndereco().equals(tipoEnderecoBD)) {
				countTipoEndereco++;
			}
		}
		if(countTipoEndereco < 2) {
			return "É necessário ter pelo menos um endereço do tipo " + tipoEnderecoBD.getDescricao() + " no cadastro";
		}
		return null;
	}

}
