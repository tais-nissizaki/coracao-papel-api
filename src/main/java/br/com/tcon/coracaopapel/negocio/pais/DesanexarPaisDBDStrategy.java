package br.com.tcon.coracaopapel.negocio.pais;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pais;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarPaisDBDStrategy implements IStrategy {
	
	private EntityManager entityManager;

	public DesanexarPaisDBDStrategy(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Pais pais = (Pais) entidade;
		pais.setEstados(null);
		entityManager.detach(pais);
		return null;
	}

}
