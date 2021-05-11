package br.com.tcon.coracaopapel.negocio.carrinho;

import javax.persistence.EntityManager;

import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;
import br.com.tcon.coracaopapel.negocio.cliente.DesanexarClienteBDStrategy;

public class DesanexarCarrinhoBDStrategy implements IStrategy {
	
	private EntityManager entityManager;
	private DesanexarClienteBDStrategy desanexarClienteBDStrategy;
	
	public DesanexarCarrinhoBDStrategy(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.desanexarClienteBDStrategy = new DesanexarClienteBDStrategy(entityManager);
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		if(carrinho != null) {
			if(carrinho.getItensCarrinho() != null) {
				for(int i=0; i<carrinho.getItensCarrinho().size(); i++) {
					carrinho.getItensCarrinho().get(i).setCarrinho(null);
					carrinho.getItensCarrinho().get(i).getProduto();
					entityManager.detach(carrinho.getItensCarrinho().get(i));
				}
			}
			if(carrinho.getCliente() != null && carrinho.getCliente().getId() != null) {
				this.desanexarClienteBDStrategy.processar(carrinho.getCliente());
			}
			entityManager.detach(entidade);
		}
		return null;
	}

}
