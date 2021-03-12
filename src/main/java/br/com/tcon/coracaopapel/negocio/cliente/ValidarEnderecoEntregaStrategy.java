package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarEnderecoEntregaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		if(entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			boolean encontrouCobranca = false;
			if(cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {
				for(int i=0; i< cliente.getEnderecos().size(); i++) {
					Endereco endereco = cliente.getEnderecos().get(i);
					if (endereco.getTipoEndereco().getId() != null && endereco.getTipoEndereco().getId() == 1) {
						encontrouCobranca = true;
						break;
					}
				}
			}
			if (!encontrouCobranca) {
				retorno.append("É necessário o cadastro de ao menos um endereço de entrega.");
			}
		}
		
		return retorno.toString();
	}

}
