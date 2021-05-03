package br.com.tcon.coracaopapel.modelo.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cupom")
public class Cupom extends EntidadeDominio {

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "percentual")
	private BigDecimal percentual;

	@Column(name = "inicio_vigencia")
	@Temporal(TemporalType.DATE)
	private Date inicioVigencia;

	@Column(name = "final_vigencia")
	@Temporal(TemporalType.DATE)
	private Date finalVigencia;
	
	@OneToOne(mappedBy = "cupom", fetch = FetchType.LAZY)
	private CupomCliente cliente;

	@OneToMany(mappedBy = "cupom", fetch = FetchType.LAZY)
	private List<CupomPedido> pedido;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getPercentual() {
		return percentual;
	}

	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}

	public Date getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public Date getFinalVigencia() {
		return finalVigencia;
	}

	public void setFinalVigencia(Date finalVigencia) {
		this.finalVigencia = finalVigencia;
	}

	public CupomCliente getCliente() {
		return cliente;
	}

	public void setCliente(CupomCliente cliente) {
		this.cliente = cliente;
	}

	public List<CupomPedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<CupomPedido> pedido) {
		this.pedido = pedido;
	}

}
