package br.com.tcon.coracaopapel.modelo.dominio;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.Base64Utils;

@Entity
@Table(name = "produto")
public class Produto extends EntidadeDominio {
	
	@Column(name="titulo")
	private String titulo;
	
	@Lob
	@Column(name="imagem")
    private byte[] imagem;
	
	@Transient
	private String imageBase64;
	
	@Column(name="valor")
	private BigDecimal valor;
	
	@Column(name="codigo_barras")
	private String codigoBarras;
	
	@Lob 
	@Column(name="sinopse")
	private String sinopse;
	
	@Column(name="numero_paginas")
	private Integer numeroPaginas;

	@Column(name="isbn")
	private String isbn;
	
	@Column(name="edicao")
	private String edicao;
	
	@Column(name="editora")
	private String editora;
	
	@Column(name="ano_edicao")
	private Integer anoEdicao;
	
	@Column(name="autor")
	private String autor;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@Column(name = "ativo")
	private Boolean ativo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_dimensao")
	private Dimensao dimensao;

	@OneToOne
	@JoinColumn(name = "id_grupo_precificacao")
	private GrupoPrecificacao grupoPrecificacao;

	@OneToMany
	@JoinTable(
			name="produto_categoria",
			joinColumns = @JoinColumn(name = "id_produto"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria")
	)
	private List<Categoria> categorias;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
		if(this.imagem != null) {
			this.imageBase64 = Base64Utils.encodeToString(this.imagem);
		}
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public Integer getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(Integer numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Integer getAnoEdicao() {
		return anoEdicao;
	}

	public void setAnoEdicao(Integer anoEdicao) {
		this.anoEdicao = anoEdicao;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Dimensao getDimensao() {
		return dimensao;
	}

	public void setDimensao(Dimensao dimensao) {
		this.dimensao = dimensao;
	}

	public GrupoPrecificacao getGrupoPrecificacao() {
		return grupoPrecificacao;
	}

	public void setGrupoPrecificacao(GrupoPrecificacao grupoPrecificacao) {
		this.grupoPrecificacao = grupoPrecificacao;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getImageBase64() {
		if(imagem != null) {
			return Base64Utils.encodeToString(imagem);
		}
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
		if(imageBase64 != null) {
			if(imageBase64.contains("data:image") && (imageBase64.contains("base64"))) {
				this.imagem = Base64Utils.decodeFromString(imageBase64.split(",")[1]);
			} else {
				this.imagem = Base64Utils.decodeFromString(imageBase64);
			}
		}
	}
	
}
