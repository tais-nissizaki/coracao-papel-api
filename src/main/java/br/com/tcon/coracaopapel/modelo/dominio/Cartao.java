package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "cartao")
public class Cartao extends EntidadeDominio {

	@Column(name = "numero")
	private String numero;

	@Column(name = "cvv")
	private String cvv;

	@Column(name = "nome_impresso")
	private String nomeImpresso;

	@Column(name = "dt_validade")
	@Temporal(TemporalType.DATE)
	private Date dataValidade;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_cartao")
	private TipoCartao tipoCartao;
	
	@ManyToOne
	@JoinColumn(name = "id_bandeira_cartao")
	private BandeiraCartao bandeiraCartao;

	@ManyToOne
	@JoinTable(
			name="cliente_cartao",
			joinColumns = @JoinColumn(name = "id_cartao", insertable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "id_cliente", insertable = false, updatable = false)
	)
	private Cliente cliente;
	
	@Transient
	private boolean desejaCadastrarCartao;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getNomeImpresso() {
		return nomeImpresso;
	}

	public void setNomeImpresso(String nomeImpresso) {
		this.nomeImpresso = nomeImpresso;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public TipoCartao getTipoCartao() {
		return tipoCartao;
	}

	public void setTipoCartao(TipoCartao tipoCartao) {
		this.tipoCartao = tipoCartao;
	}

	public BandeiraCartao getBandeiraCartao() {
		return bandeiraCartao;
	}

	public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isDesejaCadastrarCartao() {
		return desejaCadastrarCartao;
	}

	public void setDesejaCadastrarCartao(boolean desejaCadastrarCartao) {
		this.desejaCadastrarCartao = desejaCadastrarCartao;
	}

}
