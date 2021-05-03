package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.Date;
import java.util.List;

import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomPedido;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class PrencherDataCadastroStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Date dtCadastro = new Date();
		entidade.setDtCadastro(dtCadastro);
		Pedido pedido = (Pedido) entidade;
		
		if(pedido.getIdStatusPedido() != null && pedido.getIdStatusPedido().equals(6)) {
			
		} else {
			if (pedido.getEnderecoEntrega().getEndereco().getId() == null) {
				pedido.getEnderecoEntrega().getEndereco().setDtCadastro(dtCadastro);
			} else {
				pedido.getEnderecoEntrega().setPedido(pedido);
			}
			pedido.getEnderecoEntrega().setDtCadastro(dtCadastro);
			
			if(pedido.getPedidoPagamentos()!= null) {
				for(PedidoPagamento pedidoPagamento: pedido.getPedidoPagamentos()) {
					pedidoPagamento.setDtCadastro(dtCadastro);
					pedidoPagamento.setPedido(pedido);
					if(pedidoPagamento.getCartao().getId() == null) {
						pedidoPagamento.getCartao().setDtCadastro(dtCadastro);
					} else {
						pedidoPagamento.setPedido(pedido);
					}
				}
			}
			
			for(ItemPedido itemPedido: pedido.getItensPedido()) {
				itemPedido.setPedido(pedido);
				itemPedido.setDtCadastro(dtCadastro);
			}
			if(pedido.getCupons() != null) {
				for(CupomPedido cupomPedido: pedido.getCupons()) {
					cupomPedido.setDtCadastro(dtCadastro);
					cupomPedido.setPedido(pedido);
				}
			}
		}
		return null;
	}

}
