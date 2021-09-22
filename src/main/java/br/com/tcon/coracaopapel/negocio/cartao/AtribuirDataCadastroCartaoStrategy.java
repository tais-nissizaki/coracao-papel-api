package br.com.tcon.coracaopapel.negocio.cartao;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirDataCadastroCartaoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDtCadastro(new Date());
		((Cartao)entidade).setAtivo(true);
		return null;
	}

}
