package br.com.tcon.coracaopapel.negocio.pedido;

import java.math.BigDecimal;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.endereco.ValidarEnderecoStrategy;

public class ValidarEnderecoEntregaPedidoStrategy implements IStrategy {
	
	private ValidarEnderecoStrategy validadorEnderecoStrategy = new ValidarEnderecoStrategy();

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		StringBuilder erros = new StringBuilder();

		if(pedido.getIdStatusPedido() == null || !pedido.getIdStatusPedido().equals(6)) {	
			if(pedido.getEnderecoEntrega() == null) {
				erros.append("Endereço não infomado.").append("\n");
			} else {
				if(pedido.getEnderecoEntrega().getValorFrete() == null || pedido.getEnderecoEntrega().getValorFrete().compareTo(BigDecimal.ZERO) < 0) {
					erros.append("Valor de frete invalido.").append("\n");
				} 
				if (pedido.getEnderecoEntrega().getEndereco() == null){
					erros.append("Endereço não infomado.").append("\n");
				} else if(pedido.getEnderecoEntrega().getEndereco() == null) {
					erros.append( validadorEnderecoStrategy.processar(pedido.getEnderecoEntrega().getEndereco()));
				}
				
			}
		}
		
		return erros.toString();
	}

}
