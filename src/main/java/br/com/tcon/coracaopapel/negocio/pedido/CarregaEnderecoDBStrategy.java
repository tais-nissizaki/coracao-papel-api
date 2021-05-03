package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.List;

import br.com.tcon.coracaopapel.dao.EnderecoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CarregaEnderecoDBStrategy implements IStrategy {
	
	private EnderecoDAO enderecoDAO;
	
	public CarregaEnderecoDBStrategy(EnderecoDAO enderecoDAO) {
		this.enderecoDAO = enderecoDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		if (pedido.getEnderecoEntrega() != null && pedido.getEnderecoEntrega().getEndereco().getId() != null) {
			List<EntidadeDominio> enderecos = enderecoDAO.consultar(pedido.getEnderecoEntrega().getEndereco());
			if(enderecos != null && !enderecos.isEmpty()) {
				pedido.getEnderecoEntrega().setEndereco((Endereco)enderecos.get(0));
			}
		}
		return null;
	}

}
