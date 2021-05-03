package br.com.tcon.coracaopapel.negocio.pedido;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarItemPedidoDBStrategy implements IStrategy {

	private EntityManager entityManager;
	
	public DesanexarItemPedidoDBStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	@Override
	public String processar(EntidadeDominio entidade) {
		ItemPedido itemPedido = (ItemPedido) entidade;
		entityManager.detach(itemPedido);
		itemPedido.setPedido(null);
		return null;
	}

}
