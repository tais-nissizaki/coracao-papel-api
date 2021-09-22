package br.com.tcon.coracaopapel.negocio.endereco;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirDataCadastroStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDtCadastro(new Date());
		((Endereco)entidade).setAtivo(true);
		return null;
	}

}
