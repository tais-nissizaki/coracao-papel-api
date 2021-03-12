package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade extends EntidadeDominio {
	
	@Column(name = "descricao")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_estado")
	private Estado estado;
//	
//	@OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	private List<Endereco> endereco;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

//	public List<Endereco> getEndereco() {
//		return endereco;
//	}
//
//	public void setEndereco(List<Endereco> endereco) {
//		this.endereco = endereco;
//	}
}
