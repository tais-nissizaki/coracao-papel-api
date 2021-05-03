package br.com.tcon.coracaopapel.negocio.pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.cartao.ValidarDadosCartaoStrategy;

public class ValidarDadosPagamentoStrategy implements IStrategy {
	
	private ValidarDadosCartaoStrategy validarDadosPagamentoStrategy = new ValidarDadosCartaoStrategy();
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		StringBuilder erros = new StringBuilder();
		if(pedido.getIdStatusPedido() != null && pedido.getIdStatusPedido().equals(6)) {
			return erros.toString();
		}
		
		// Validar se existe forma de pagamento
		if(pedido.getPedidoPagamentos() == null || pedido.getPedidoPagamentos().isEmpty()) {
			return "Não existem dados de pagamento na solicitação do pedido.";
		} else {
			BigDecimal somaPagamento = BigDecimal.ZERO;
			for(PedidoPagamento pedidoPagamento: pedido.getPedidoPagamentos()) {
				// Validar se as formas de pagamento possuem valor
				if (pedidoPagamento.getValor() == null || pedidoPagamento.getValor().compareTo(BigDecimal.ZERO) <= 0) {
					erros.append("Não foi informado um valor para o pagamento.").append("\n");
				} else {
					somaPagamento = somaPagamento.add(pedidoPagamento.getValor());
				}
				
				// Validar se as formas de pagamento possuem cartão
				if(pedidoPagamento.getCartao() == null) {
					erros.append("Não foi informado um cartão para o pagamento").append("\n");
				} else if(pedidoPagamento.getCartao().getId() == null) {
					// Validar dados do cartão não cadastrado 
					erros.append(validarDadosPagamentoStrategy.processar(pedidoPagamento.getCartao()));
				}
			}
			
			// Validar Soma das formas de pagamento == valor do pedido
			if(!pedido.getValorTotal().setScale(2, RoundingMode.HALF_EVEN).equals(somaPagamento.setScale(2, RoundingMode.HALF_EVEN))) {
				erros.append("O valor do pagamento não condiz com o valor do pedido.").append("\n");
			}
		}
		
		return erros.toString();
	}

}
