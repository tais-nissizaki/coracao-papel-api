package br.com.tcon.coracaopapel.negocio.cupom;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirDataDeCadastroCupom implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDtCadastro(new Date());
		return null;
	}

}
