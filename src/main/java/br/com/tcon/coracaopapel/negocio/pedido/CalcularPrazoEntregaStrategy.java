package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CalcularPrazoEntregaStrategy implements IStrategy {
	
	private Map<String, Integer> prazoEntregaPorEstado = new HashMap<>();
	
	public CalcularPrazoEntregaStrategy() {
		prazoEntregaPorEstado.put("RO", 9);
		prazoEntregaPorEstado.put("AC", 9);
		prazoEntregaPorEstado.put("AM", 9);
		prazoEntregaPorEstado.put("RR", 9);
		prazoEntregaPorEstado.put("PA", 9);
		prazoEntregaPorEstado.put("AP", 9);
		prazoEntregaPorEstado.put("TO", 8);
		prazoEntregaPorEstado.put("MA", 8);
		prazoEntregaPorEstado.put("PI", 8);
		prazoEntregaPorEstado.put("CE", 8);
		prazoEntregaPorEstado.put("RN", 8);
		prazoEntregaPorEstado.put("PB", 8);
		prazoEntregaPorEstado.put("PE", 8);
		prazoEntregaPorEstado.put("AL", 85);
		prazoEntregaPorEstado.put("SE", 8);
		prazoEntregaPorEstado.put("BA", 5);
		prazoEntregaPorEstado.put("MG", 6);
		prazoEntregaPorEstado.put("ES", 6);
		prazoEntregaPorEstado.put("RJ", 6);
		prazoEntregaPorEstado.put("SP", 5);
		prazoEntregaPorEstado.put("PR", 6);
		prazoEntregaPorEstado.put("SC", 6);
		prazoEntregaPorEstado.put("RS", 6);
		prazoEntregaPorEstado.put("MS", 7);
		prazoEntregaPorEstado.put("MT", 7);
		prazoEntregaPorEstado.put("GO", 7);
		prazoEntregaPorEstado.put("DF", 7);
	}
	

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
		if(pedido.getIdStatusPedido() == null || !pedido.getIdStatusPedido().equals(6)) {			
			String siglaEstado = pedido.getEnderecoEntrega().getEndereco().getCidade().getEstado().getDescricao();
			Integer prazo = prazoEntregaPorEstado.get(siglaEstado);
			Calendar dtEntrega = Calendar.getInstance();
			dtEntrega.add(Calendar.DAY_OF_YEAR, prazo);
			pedido.getEnderecoEntrega().setDataEntrega(dtEntrega.getTime());
		}
		return null;
	}
}
