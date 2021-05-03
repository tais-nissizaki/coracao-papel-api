package br.com.tcon.coracaopapel.negocio.pedido;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemPedido;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarProdutosCarrinhoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		StringBuilder erros = new StringBuilder();
		if(pedido.getItensPedido() == null || pedido.getItensPedido().isEmpty()) {
			erros.append("Pedidos sem itens.").append("\n");
		} else {
			for(ItemPedido itemPedido: pedido.getItensPedido()) {
				if (itemPedido.getQuantidade() == null || itemPedido.getQuantidade() < 1) {
					erros.append("Quantidade inválida.").append("\n");
				}
				
				if(itemPedido.getProduto() == null) {
					erros.append("Produto inválido.").append("\n");
				}
			}
		}
		return erros.toString();
	}

}
