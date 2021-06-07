package br.com.tcon.coracaopapel.negocio.pedido;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.CupomPedido;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarPedidoCupomBDStrategy implements IStrategy {

	private EntityManager entityManager;
	
	public DesanexarPedidoCupomBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public String processar(EntidadeDominio entidade) {
		CupomPedido cupom = (CupomPedido)entidade;
		entityManager.detach(cupom);
		cupom.getCupom().setCliente(null);
		cupom.getCupom().setPedido(null);
//		entityManager.detach(cupom);
//		cupom.setPedido(null);
		return null;
	}

}
