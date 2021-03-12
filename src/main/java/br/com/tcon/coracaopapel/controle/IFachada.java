package br.com.tcon.coracaopapel.controle;

import java.util.List;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

public interface IFachada {

	public String salvar(EntidadeDominio entidade);
	
	public String alterar(EntidadeDominio entidade);
	
	public String inativar(EntidadeDominio entidade);

	public String ativar(EntidadeDominio entidade);
	
	public List<EntidadeDominio> consultar(EntidadeDominio entidade);
}
