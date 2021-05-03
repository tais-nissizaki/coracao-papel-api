package br.com.tcon.coracaopapel.modelo.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dimensao")
public class Dimensao extends EntidadeDominio {

	@Column(name = "altura", precision = 4, scale = 2)
	private BigDecimal altura;

	@Column(name = "largura", precision = 4, scale = 2)
	private BigDecimal largura;

	@Column(name = "profundidade", precision = 4, scale = 2)
	private BigDecimal profundidade;

	@Column(name = "peso", precision = 6, scale = 3)
	private BigDecimal peso;

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public BigDecimal getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(BigDecimal profundidade) {
		this.profundidade = profundidade;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

}
