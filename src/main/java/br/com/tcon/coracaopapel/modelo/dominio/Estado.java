package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado extends EntidadeDominio{
	
	@Column(name = "descricao")
	private String descricao;
	
	
//	@OneToMany(mappedBy = "estado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Cidade> cidades;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

//	public List<Cidade> getCidades() {
//		return cidades;
//	}
//
//	public void setCidades(List<Cidade> cidades) {
//		this.cidades = cidades;
//	}
}
