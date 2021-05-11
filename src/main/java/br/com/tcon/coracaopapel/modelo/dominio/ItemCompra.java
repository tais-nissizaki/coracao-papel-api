package br.com.tcon.coracaopapel.modelo.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_compra")
public class ItemCompra extends EntidadeDominio {

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "valor_compra")
	private BigDecimal valorCompra;

	@ManyToOne
	@JoinColumn(name = "id_compra")
	private Compra compra;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
