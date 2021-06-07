package br.com.tcon.coracaopapel.modelo.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "telefone")
public class Telefone extends EntidadeDominio {

	@Column(name = "numero")
	private String numero;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_telefone")
	private TipoTelefone tipoTelefone;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

}
