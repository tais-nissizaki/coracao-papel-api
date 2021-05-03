package br.com.tcon.coracaopapel.negocio.pedido;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirStatusInicialPedidoStrategy implements IStrategy {
	
	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		if(pedido.getIdStatusPedido() == null) {
			pedido.setIdStatusPedido(1);
		}
		return null;
	}

}
