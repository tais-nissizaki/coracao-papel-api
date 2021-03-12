package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class EntidadeDominio {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="dtCadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCadastro;
	
//	@Column(name="dtAlteracao")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date dtAlteracao;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

//	public Date getDtAlteracao() {
//		return dtAlteracao;
//	}
//
//	public void setDtAlteracao(Date dtAlteracao) {
//		this.dtAlteracao = dtAlteracao;
//	}

}
