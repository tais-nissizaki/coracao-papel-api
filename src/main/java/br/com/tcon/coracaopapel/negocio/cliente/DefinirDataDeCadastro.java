package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirDataDeCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			cliente.setDtCadastro(new Date());
			if(cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
				for( int i=0; i< cliente.getDocumentos().size(); i++) {
					cliente.getDocumentos().get(i).setDtCadastro(new Date());
				}
			}
			if(cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {
				for( int i=0; i< cliente.getEnderecos().size(); i++) {
					cliente.getEnderecos().get(i).setDtCadastro(new Date());
				}
			}
		}
		return null;
	}

}