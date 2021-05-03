package br.com.tcon.coracaopapel.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CupomDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public boolean inativar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public boolean ativar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		if(entidade.getId() != null) {
			return List.of(entityManager.find(Cupom.class, entidade.getId()));
		}
		
		Cupom cupom = (Cupom) entidade;
		StringBuilder queryString = new StringBuilder("SELECT DISTINCT c FROM Cupom c LEFT JOIN c.cliente cc ");
		Map<String, Object> parametros = new HashMap<>();
		 
		if(cupom.getCodigo() != null && !cupom.getCodigo().isBlank()) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.codigo = :codigo ");
			parametros.put("codigo", cupom.getCodigo());
		}

		if(cupom.getInicioVigencia() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.inicioVigencia <= :inicioVigencia ");
			parametros.put("inicioVigencia", cupom.getInicioVigencia());
		}

		if(cupom.getFinalVigencia() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.finalVigencia >= :finalVigencia ");
			parametros.put("finalVigencia", cupom.getFinalVigencia());
		}
		
		if(cupom.getCliente() != null && cupom.getCliente().getCliente() != null && cupom.getCliente().getCliente().getId() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("( (c.valor IS NOT NULL AND cc.cliente.id = :idCliente) ");
			queryString.append("OR (c.valor IS NULL AND cc.cliente.id IS NULL)) ");
			parametros.put("idCliente", cupom.getCliente().getCliente().getId());
			
		} else {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.valor IS NULL AND cc.cliente.id IS NULL ");
		}
		
		Query query = entityManager.createQuery(queryString.toString(), Cupom.class);
		for(Entry<String, Object> entry: parametros.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List resultList = query.getResultList();
		return resultList;
	}

}
