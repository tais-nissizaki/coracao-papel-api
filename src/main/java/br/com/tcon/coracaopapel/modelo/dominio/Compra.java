package br.com.tcon.coracaopapel.modelo.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "compra")
public class Compra extends EntidadeDominio {
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "compra")
	private List<ItemCompra> itensCompra;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<ItemCompra> getItensCompra() {
		return itensCompra;
	}

	public void setItensCompra(List<ItemCompra> itensCompra) {
		this.itensCompra = itensCompra;
	}

}
