package br.com.tcon.coracaopapel.view.dto;

import java.util.List;

public class DadosUsuario {

	private Integer idCliente;
	private List<String> permissoes;
	private boolean ativo;

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public List<String> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<String> perfis) {
		this.permissoes = perfis;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
