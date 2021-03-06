package br.com.tcon.coracaopapel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CarrinhoDAO implements IDAO {

	@Autowired
	protected EntityManager entityManager;

	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		entityManager.persist(carrinho);
		entityManager.flush();
		return true;
	}

	@Override
	@Transactional
	public boolean alterar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		entidade = entityManager.merge(carrinho);
		entityManager.flush();
		return true;
	}

	@Override
	@Transactional
	public boolean inativar(EntidadeDominio entidade) {
		Query query = entityManager.createQuery("DELETE FROM Carrinho c where c.id = :idCarrinho")
			.setParameter("idCarrinho", entidade.getId());
		if(query.executeUpdate() <= 0) {			
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean ativar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		if(carrinho != null) {
			List<EntidadeDominio> listaRetorno = new ArrayList<>();
			if(carrinho.getId() == null) {
				StringBuilder queryString = new StringBuilder("SELECT c FROM Carrinho c ");
				queryString.append(" LEFT JOIN c.cliente cl ");
				queryString.append(" LEFT JOIN c.itensCarrinho ic ");
				queryString.append(" LEFT JOIN ic.produto p ");
				Map<String, Object> parametros = new HashMap<>();
				boolean adicionouWhere = false;
				if (carrinho.getCliente() != null && carrinho.getCliente().getId() != null) {
					if(!adicionouWhere) {
						queryString.append(" WHERE ");
						adicionouWhere = true;
					}
					queryString.append(" cl.id = :idCliente ");
					parametros.put("idCliente", carrinho.getCliente().getId());
				}
				
				if(carrinho.getItensCarrinho() != null && !carrinho.getItensCarrinho().isEmpty()) {
					if(!adicionouWhere) {
						queryString.append(" WHERE ");
						adicionouWhere = true;
					} else {
						queryString.append(" AND ");
					}
					
					if (carrinho.getItensCarrinho().get(0).getProduto().getId() != null && carrinho.getItensCarrinho().get(0).getProduto().getId() > 0) {
						queryString.append(" p.id = :idProduto");
						parametros.put("idProduto", carrinho.getItensCarrinho().get(0).getProduto().getId());
					} else { 
						queryString.append(" p.titulo like :tituloProduto ");
						parametros.put("tituloProduto", "%" + carrinho.getItensCarrinho().get(0).getProduto().getTitulo() + "%");
					}
				}
				
				Query query = entityManager.createQuery(queryString.toString());
				for (Entry<String, Object> chaveValor: parametros.entrySet()) {
					query.setParameter(chaveValor.getKey(), chaveValor.getValue());
				}
				return query.getResultList();
			} else {
				listaRetorno.add(entityManager.find(Carrinho.class, carrinho.getId()));
			}
			return listaRetorno;
		}
		return null;
	}

}
