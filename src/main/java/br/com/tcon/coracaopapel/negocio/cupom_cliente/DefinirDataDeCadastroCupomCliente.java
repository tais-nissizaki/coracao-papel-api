package br.com.tcon.coracaopapel.negocio.cupom_cliente;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.cupom.DefinirDataDeCadastroCupom;

public class DefinirDataDeCadastroCupomCliente implements IStrategy {
	
	private DefinirDataDeCadastroCupom definirDataDeCadastroCupom;

	public DefinirDataDeCadastroCupomCliente() {
		super();
		this.definirDataDeCadastroCupom = new DefinirDataDeCadastroCupom();
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		entidade.setDtCadastro(new Date());
		this.definirDataDeCadastroCupom.processar(((CupomCliente)entidade).getCupom());
		((CupomCliente)entidade).setUtilizado(false);
		return null;
	}

}
