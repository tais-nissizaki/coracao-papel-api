package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.List;

import br.com.tcon.coracaopapel.dao.CartaoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CarregarCartaoDBStrategy implements IStrategy {
	
	private CartaoDAO cartaoDAO;
	
	public CarregarCartaoDBStrategy(CartaoDAO cartaoDAO) {
		this.cartaoDAO = cartaoDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		if(pedido.getPedidoPagamentos() != null && !pedido.getPedidoPagamentos().isEmpty()) {
			for(PedidoPagamento pedidoPagamento: pedido.getPedidoPagamentos()) {
				if(pedidoPagamento.getCartao().getId() != null) {
					List<EntidadeDominio> consultar = cartaoDAO.consultar(pedidoPagamento.getCartao());
					if(consultar != null && !consultar.isEmpty()) {
						pedidoPagamento.setCartao((Cartao)consultar.get(0));
					}
				}
			}
		}
		return null;
	}

}
