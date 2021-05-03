package br.com.tcon.coracaopapel.negocio.fornecedor;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Fornecedor;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarFornecedorBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	
	public DesanexarFornecedorBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Fornecedor fornecedor = (Fornecedor) entidade;
		if(fornecedor != null) {
			fornecedor.setCompras(null);
			entityManager.detach(fornecedor);
		}
		return null;
	}

}
