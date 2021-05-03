package br.com.tcon.coracaopapel.negocio.cupom;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DesanexarCupomBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	
	public DesanexarCupomBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Cupom cupom = (Cupom) entidade;
		if(cupom != null) {
			if(cupom.getCliente() != null) {				
				entityManager.detach(cupom.getCliente());
				cupom.getCliente().setCliente(null);
				cupom.getCliente().setCupom(null);
			}
			entityManager.detach(cupom);
			cupom.setPedido(null);
		}
		return null;
	}

}
