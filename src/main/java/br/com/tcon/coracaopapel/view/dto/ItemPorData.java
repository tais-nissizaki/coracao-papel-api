package br.com.tcon.coracaopapel.view.dto;

import java.util.Date;

public class ItemPorData {
	private Date data;
	private int quantidade;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public void adicionarQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

}
