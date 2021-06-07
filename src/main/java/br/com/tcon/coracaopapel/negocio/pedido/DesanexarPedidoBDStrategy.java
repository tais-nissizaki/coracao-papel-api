package br.com.tcon.coracaopapel.negocio.pedido;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomPedido;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.modelo.dominio.TransacaoPedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.cartao.DesanexarCartaoBDStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.DesanexarClienteBDStrategy;
import br.com.tcon.coracaopapel.negocio.endereco.DesanexarEnderecoBDStrategy;

public class DesanexarPedidoBDStrategy implements IStrategy {

	private EntityManager entityManager;
	private DesanexarEnderecoBDStrategy desanexarEnderecoBDStrategy;
	private DesanexarCartaoBDStrategy desanexarCartaoBDStrategy;
	private DesanexarClienteBDStrategy desanexarClienteBDStrategy;
	private DesanexarItemPedidoBDStrategy desanexarItemPedidoDBStrategy;
	private DesanexarPedidoCupomBDStrategy desanexarPedidoCupomDBStrategy;
	private DesanexarPedidoPagamentoBDStrategy desanexarPedidoPagamentoDBStrategy;
	
	public DesanexarPedidoBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.desanexarEnderecoBDStrategy = new DesanexarEnderecoBDStrategy(entityManager);
		this.desanexarCartaoBDStrategy = new DesanexarCartaoBDStrategy(entityManager);
		this.desanexarClienteBDStrategy = new DesanexarClienteBDStrategy(entityManager);
		this.desanexarItemPedidoDBStrategy = new DesanexarItemPedidoBDStrategy(entityManager);
		this.desanexarPedidoCupomDBStrategy = new DesanexarPedidoCupomBDStrategy(entityManager);
		this.desanexarPedidoPagamentoDBStrategy = new DesanexarPedidoPagamentoBDStrategy(entityManager);
	}
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido)entidade;
		desanexarClienteBDStrategy.processar(pedido.getCliente());
		if(pedido.getEnderecoEntrega() != null) {
			desanexarEnderecoBDStrategy.processar(pedido.getEnderecoEntrega().getEndereco());
			entityManager.detach(pedido.getEnderecoEntrega());
			pedido.getEnderecoEntrega().setPedido(null);
		}
		if(pedido.getPedidoPagamentos() != null) {
			for(PedidoPagamento pedidoPagamento: pedido.getPedidoPagamentos()) {
				desanexarCartaoBDStrategy.processar(pedidoPagamento.getCartao());
			}
		}
		
		for(ItemPedido itemPedido: pedido.getItensPedido()) {
			desanexarItemPedidoDBStrategy.processar(itemPedido);
		}
		if(pedido.getCupons() != null) {			
			for(CupomPedido cupom: pedido.getCupons()) {
				desanexarPedidoCupomDBStrategy.processar(cupom);
				entityManager.detach(cupom);
				cupom.setPedido(null);
			}
		}
		if(pedido.getPedidoPagamentos() != null) {			
			for(PedidoPagamento pedidoPagamento :pedido.getPedidoPagamentos()) {
				this.desanexarPedidoPagamentoDBStrategy.processar(pedidoPagamento);
			}
		}
		if(pedido.getTransacoesPedido() != null) {
			for(TransacaoPedido transacao: pedido.getTransacoesPedido()) {
				entityManager.detach(transacao);
				transacao.setPedido(null);
			}
		}
		entityManager.detach(entidade);
		return null;
	}

}
