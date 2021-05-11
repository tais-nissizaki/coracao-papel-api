package br.com.tcon.coracaopapel.negocio.compra;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Compra;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCompra;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirDataCadastroCompraStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Compra compra = (Compra) entidade;
		Date dtCadastro = new Date();
		compra.setDtCadastro(dtCadastro);
		for(ItemCompra itemCompra: compra.getItensCompra()) {
			itemCompra.setDtCadastro(dtCadastro);
			itemCompra.setCompra(compra);
		}
		return null;
	}

}
