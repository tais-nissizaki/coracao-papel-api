package br.com.tcon.coracaopapel.negocio.compra;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Compra;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCompra;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CarregarProdutoItemCompraStrategy implements IStrategy {
	
	private EntityManager entityManager;

	public CarregarProdutoItemCompraStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Compra compra = (Compra) entidade;
		for(ItemCompra itemCompra: compra.getItensCompra()) {
			itemCompra.setProduto(this.entityManager.find(Produto.class, itemCompra.getProduto().getId()));
		}
		return null;
	}

}
