package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarEnderecoCobrancaStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		StringBuilder retorno = new StringBuilder();
		if(entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			boolean encontrouCobranca = false;
			if(cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {
				for (Endereco endereco: cliente.getEnderecos()) {
					if (endereco.getTipoEndereco().getId() != null && endereco.getTipoEndereco().getId() == 2) {
						encontrouCobranca = true;
						break;
					}
				}
			}
			if (!encontrouCobranca) {
				retorno.append("É necessário o cadastro de ao menos um endereço de cobrança.");
			}
		}
		
		return retorno.toString();
	}

}
