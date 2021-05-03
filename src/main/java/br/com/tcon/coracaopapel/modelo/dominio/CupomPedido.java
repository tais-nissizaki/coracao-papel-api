package br.com.tcon.coracaopapel.modelo.dominio;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cupom_pedido")
public class CupomPedido extends EntidadeDominio {

	@OneToOne
	@JoinColumn(name = "id_Pedido")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_cupom")
	private Cupom cupom;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Cupom getCupom() {
		return cupom;
	}

	public void setCupom(Cupom cupom) {
		this.cupom = cupom;
	}


}
