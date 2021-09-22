package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirDataDeCadastro implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if (entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			cliente.setDtCadastro(new Date());
			if (cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
				for (int i = 0; i < cliente.getDocumentos().size(); i++) {
					cliente.getDocumentos().get(i).setDtCadastro(new Date());
				}
			}
			if (cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {
				for (Endereco endereco: cliente.getEnderecos()) {
					endereco.setDtCadastro(new Date());
					endereco.setAtivo(true);
					endereco.setCliente(cliente);
				}
			}
			if (cliente.getUsuario() != null) {
				cliente.getUsuario().setDtCadastro(new Date());
			}
			if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
				for (int i = 0; i < cliente.getTelefones().size(); i++) {
					cliente.getTelefones().get(i).setDtCadastro(new Date());
				}
			}
			if (cliente.getCartoes() != null && !cliente.getCartoes().isEmpty()) {
				for (int i = 0; i < cliente.getCartoes().size(); i++) {
					cliente.getCartoes().get(i).setDtCadastro(new Date());
					cliente.getCartoes().get(i).setAtivo(true);
				}
			}
		}
		return null;
	}

}
