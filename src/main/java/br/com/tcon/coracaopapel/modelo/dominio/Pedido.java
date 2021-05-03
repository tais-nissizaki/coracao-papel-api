package br.com.tcon.coracaopapel.modelo.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeDominio {

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@OneToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@OneToOne
	@JoinColumn(name = "id_status_pedido", insertable = false, updatable = false)
	private StatusPedido statusPedido;
	
	@Column(name = "id_status_pedido", insertable = true, updatable = true)
	private Integer idStatusPedido;
	
	@Column(name = "troca_solicitada", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean trocaSolicitada;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itensPedido;

//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinTable(
//			name = "cupom_pedido", 
//			joinColumns = @JoinColumn(name = "id_pedido"), 
//			inverseJoinColumns = @JoinColumn(name = "id_cupom"))
	@ManyToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<CupomPedido> cupons;

	@OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
	private PedidoEntrega enderecoEntrega;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<PedidoPagamento> pedidoPagamentos;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<TransacaoPedido> transacoesPedido;
	
	
	//Campos para filtro
	@Transient
	private Date inicioFiltro;
	
	@Transient
	private Date fimFiltro;

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public List<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(List<ItemPedido> itensPedido) {
		this.itensPedido = itensPedido;
	}

	public List<CupomPedido> getCupons() {
		return cupons;
	}

	public void setCupons(List<CupomPedido> cupons) {
		this.cupons = cupons;
	}

	public PedidoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(PedidoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<PedidoPagamento> getPedidoPagamentos() {
		return pedidoPagamentos;
	}

	public void setPedidoPagamentos(List<PedidoPagamento> pedidoPagamentos) {
		this.pedidoPagamentos = pedidoPagamentos;
	}

	public Date getInicioFiltro() {
		return inicioFiltro;
	}

	public void setInicioFiltro(Date inicioFiltro) {
		this.inicioFiltro = inicioFiltro;
	}

	public Date getFimFiltro() {
		return fimFiltro;
	}

	public void setFimFiltro(Date fimFiltro) {
		this.fimFiltro = fimFiltro;
	}

	public List<TransacaoPedido> getTransacoesPedido() {
		return transacoesPedido;
	}

	public void setTransacoesPedido(List<TransacaoPedido> transacoesPedido) {
		this.transacoesPedido = transacoesPedido;
	}

	public Integer getIdStatusPedido() {
		return idStatusPedido;
	}

	public void setIdStatusPedido(Integer idStatusPedido) {
		this.idStatusPedido = idStatusPedido;
	}

	public boolean isTrocaSolicitada() {
		return trocaSolicitada;
	}

	public void setTrocaSolicitada(boolean trocaSolicitada) {
		this.trocaSolicitada = trocaSolicitada;
	}
	
}
