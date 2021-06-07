package br.com.tcon.coracaopapel.negocio.carrinho;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCarrinho;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class PreencherDataCadastroStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDtCadastro(new Date());
		Carrinho carrinho = (Carrinho) entidade;
		for (ItemCarrinho itemCarrinho: carrinho.getItensCarrinho()) {
			itemCarrinho.setDtCadastro(new Date());
			itemCarrinho.setCarrinho(carrinho);
		}
		return null;
	}

}
