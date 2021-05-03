package br.com.tcon.coracaopapel.negocio.cartao;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarCartaoBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	
	public DesanexarCartaoBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Cartao cartao = (Cartao) entidade;
		entityManager.detach(entidade);
		if(cartao != null) {
			cartao.setCliente(null);
		}
		return null;
	}

}
