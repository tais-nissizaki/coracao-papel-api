package br.com.tcon.coracaopapel.negocio.cliente;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Telefone;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarClienteBDStrategy implements IStrategy {

	private EntityManager entityManager;
	
	public DesanexarClienteBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		cliente.setCarrinho(null);
		if(cliente.getCartoes() != null) {
			for (Cartao cartao: cliente.getCartoes()) {
				cartao.setCliente(null);
			}
		}
		cliente.setCuponsCliente(null);
		if(cliente.getDocumentos() != null) {
			for (Documento documento: cliente.getDocumentos()) {
				documento.setCliente(null);
			}
		}
		if(cliente.getEnderecos() != null) {
			for(Endereco endereco: cliente.getEnderecos()) {
				endereco.setCliente(null);
				endereco.getCidade().getEstado().getPais().setEstados(null);
			}
		}
		cliente.getUsuario().setCliente(null);
		cliente.getUsuario().setPermissoes(null);
		for(Telefone telefone: cliente.getTelefones()) {
		}
		entityManager.detach(cliente);
		return null;
	}

}
