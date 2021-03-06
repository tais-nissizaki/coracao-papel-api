package br.com.tcon.coracaopapel.modelo.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidade")
public class Cidade extends EntidadeDominio {
	
	@Column(name = "descricao")
	private String descricao;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "id_estado")
	private Estado estado;

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

}
