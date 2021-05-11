package br.com.tcon.coracaopapel.negocio.compra;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Compra;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Fornecedor;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CarregarFornecedorCompraStrategy implements IStrategy {
	
	private EntityManager entityManager;

	public CarregarFornecedorCompraStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Compra compra = (Compra) entidade;
		compra.setFornecedor(this.entityManager.find(Fornecedor.class, compra.getFornecedor().getId()));
		return null;
	}

}
