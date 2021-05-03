package br.com.tcon.coracaopapel.modelo.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "item_carrinho")
public class ItemCarrinho extends EntidadeDominio {

	@Column(name = "quantidade")
	private Integer quantidade;

	@ManyToOne
	@JoinColumn(name = "id_carrinho")
	@JsonBackReference
	private Carrinho carrinho;

	@OneToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
