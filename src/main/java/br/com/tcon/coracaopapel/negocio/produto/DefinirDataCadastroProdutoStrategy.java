package br.com.tcon.coracaopapel.negocio.produto;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Dimensao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirDataCadastroProdutoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if(entidade instanceof Produto) {			
			Date dtCadastro = new Date();
			entidade.setDtCadastro(dtCadastro);
			Dimensao dimensaoProduto = ((Produto)entidade).getDimensao();
			if(dimensaoProduto != null) {
				dimensaoProduto.setDtCadastro(dtCadastro);
			}
		}
		return null;
	}

}
