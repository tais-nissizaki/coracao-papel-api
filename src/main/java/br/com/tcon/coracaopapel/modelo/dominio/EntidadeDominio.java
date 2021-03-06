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
	protected Integer id;
	
	@Column(name="dt_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dtCadastro;
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadeDominio other = (EntidadeDominio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
