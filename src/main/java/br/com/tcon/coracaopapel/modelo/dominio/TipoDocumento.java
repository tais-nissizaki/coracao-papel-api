package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_documento")
public class TipoDocumento extends EntidadeDominio {
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;

//	@OneToMany(mappedBy = "tipoDocumento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Documento> documentos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

//	public List<Documento> getDocumentos() {
//		return documentos;
//	}
//
//	public void setDocumentos(List<Documento> documentos) {
//		this.documentos = documentos;
//	}
}
