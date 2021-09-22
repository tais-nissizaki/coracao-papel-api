package br.com.tcon.coracaopapel.negocio.produto;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirStatusInicialProdutoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Produto) {
			Produto produto = (Produto) entidade;
			produto.setAtivo(false);
		}
		return null;
	}

}
