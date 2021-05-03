package br.com.tcon.coracaopapel.negocio.cupom;

import java.util.Calendar;

import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirValidadeCupomStrategy implements IStrategy {
	

	@Override
	public String processar(EntidadeDominio entidade) {
		CupomCliente cupomCliente = (CupomCliente) entidade;
		Calendar calendarDataAtual = Calendar.getInstance();
		cupomCliente.getCupom().setInicioVigencia(calendarDataAtual.getTime());

		Calendar calendarDataAtualMaisUmAno = Calendar.getInstance();
		calendarDataAtualMaisUmAno.add(Calendar.YEAR, 1);
		cupomCliente.getCupom().setFinalVigencia(calendarDataAtualMaisUmAno.getTime());
		
		return null;
	}
	
}
