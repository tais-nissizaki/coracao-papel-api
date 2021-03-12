package br.com.tcon.coracaopapel.dao;

import java.util.List;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

public interface IDAO {

	public abstract boolean salvar(EntidadeDominio entidade);

	public abstract boolean alterar(EntidadeDominio entidade);

	public abstract boolean inativar(EntidadeDominio entidade);
	
	public abstract boolean ativar(EntidadeDominio entidade);

	public abstract List<EntidadeDominio> consultar(EntidadeDominio entidade);

}
