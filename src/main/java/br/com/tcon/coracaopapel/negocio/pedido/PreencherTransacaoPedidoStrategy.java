package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.TransacaoPedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class PreencherTransacaoPedidoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		if(pedido.getIdStatusPedido() != null && pedido.getIdStatusPedido().equals(6)) {
			TransacaoPedido transacao = new TransacaoPedido();
			transacao.setDescricao("Solicitação de troca efetuada");
			transacao.setDtCadastro(new Date());
			transacao.setPedido(pedido);
			List<TransacaoPedido> transacoes = new ArrayList<>();
			transacoes.add(transacao);
			pedido.setTransacoesPedido(transacoes);
			
		} else {
			TransacaoPedido transacao = new TransacaoPedido();
			transacao.setDescricao("Aguardando aprovação de pagamento");
			transacao.setDtCadastro(new Date());
			transacao.setPedido(pedido);
			List<TransacaoPedido> transacoes = new ArrayList<>();
			transacoes.add(transacao);
			pedido.setTransacoesPedido(transacoes);
		}
		return null;
	}

}
