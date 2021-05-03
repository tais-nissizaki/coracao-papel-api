package br.com.tcon.coracaopapel.negocio.cupom;

import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class GerarCodigoCupomStrategy implements IStrategy {
	
	private static final String alfanumerico = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	@Override
	public String processar(EntidadeDominio entidade) {
		CupomCliente cupomCliente = (CupomCliente) entidade;
		StringBuilder codigoAleatorio = new StringBuilder();
		for(int i=0; i<10; i++) {
			codigoAleatorio.append(gerarCaractere());
		}
		cupomCliente.getCupom().setCodigo(codigoAleatorio.toString());
		
		
		return null;
	}
	
	private char gerarCaractere() {
		int index = (int)(Math.random()*100) % 36;
		return alfanumerico.charAt(index);
	}

}
