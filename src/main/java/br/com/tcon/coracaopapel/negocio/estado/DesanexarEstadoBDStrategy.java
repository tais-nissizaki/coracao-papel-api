package br.com.tcon.coracaopapel.negocio.estado;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarEstadoBDStrategy implements IStrategy {
	
	private EntityManager entityManager;

	public DesanexarEstadoBDStrategy(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Estado estado = (Estado) entidade;
		estado.setPais(null);
		entityManager.detach(entidade);
		return null;
	}

}
