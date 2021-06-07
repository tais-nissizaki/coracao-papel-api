package br.com.tcon.coracaopapel.negocio.pedido;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarPedidoPagamentoBDStrategy implements IStrategy {

	private EntityManager entityManager;
	
	public DesanexarPedidoPagamentoBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	

	@Override
	public String processar(EntidadeDominio entidade) {
		PedidoPagamento pedidoPagamento = (PedidoPagamento) entidade;
		entityManager.detach(pedidoPagamento);
		pedidoPagamento.setPedido(null);
		return null;
	}

}
