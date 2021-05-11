package br.com.tcon.coracaopapel.negocio.cupom_cliente;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class AtribuirClienteCupomClienteStrategy implements IStrategy {
	
	private EntityManager entityManager;

	public AtribuirClienteCupomClienteStrategy(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		CupomCliente  cupomCliente = (CupomCliente) entidade;
		cupomCliente.setCliente(this.entityManager.find(Cliente.class, cupomCliente.getCliente().getId()));
		return null;
	}

}
