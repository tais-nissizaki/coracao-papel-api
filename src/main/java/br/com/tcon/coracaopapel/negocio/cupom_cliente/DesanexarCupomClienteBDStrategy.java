package br.com.tcon.coracaopapel.negocio.cupom_cliente;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarCupomClienteBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	
	public DesanexarCupomClienteBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		CupomCliente cupomCliente = (CupomCliente) entidade;
		if(cupomCliente != null) {
			entityManager.detach(cupomCliente);
			cupomCliente.setCliente(null);
			cupomCliente.getCupom().setCliente(null);
			cupomCliente.getCupom().setPedido(null);
		}
		return null;
	}

}
