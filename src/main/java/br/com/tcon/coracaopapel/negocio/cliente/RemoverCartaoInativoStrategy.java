package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.Iterator;

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class RemoverCartaoInativoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		Iterator<Cartao> iterator = cliente.getCartoes().iterator();
		while (iterator.hasNext()) {
			Cartao cartaoBD = iterator.next();
			if(cartaoBD.getAtivo() != null && !cartaoBD.getAtivo()) {
				iterator.remove();
			}
		}
		return null;
	}

}
