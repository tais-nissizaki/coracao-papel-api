package br.com.tcon.coracaopapel.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.CupomCliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CupomClienteDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		try {
			this.entityManager.persist(entidade);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inativar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ativar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		if(entidade.getId() != null) {
			return List.of(entityManager.find(CupomCliente.class, entidade.getId()));
		}
		CupomCliente cupomCliente = (CupomCliente) entidade;
		StringBuilder queryString = new StringBuilder("SELECT DISTINCT c FROM CupomCliente c LEFT JOIN c.cliente cc JOIN FETCH c.cupom ");
		Map<String, Object> parametros = new HashMap<>();
		 
		if(cupomCliente.getCupom().getInicioVigencia() != null && cupomCliente.getCupom().getFinalVigencia() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("  DATE(c.dtCadastro) BETWEEN :inicioVigencia AND :finalVigencia ");
			parametros.put("inicioVigencia", cupomCliente.getCupom().getInicioVigencia());
			parametros.put("finalVigencia", cupomCliente.getCupom().getFinalVigencia());
		}
		

		if(cupomCliente.getUtilizado() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append(" c.utilizado = :utilizado ");
			parametros.put("utilizado", cupomCliente.getUtilizado());
		}
		
		if(cupomCliente.getCliente() != null && cupomCliente.getCliente() != null && cupomCliente.getCliente().getId() != null) {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("( (c.cupom.valor IS NOT NULL AND cc.id = :idCliente) ");
			queryString.append("OR (c.cupom.valor IS NULL AND cc.id IS NULL)) ");
			parametros.put("idCliente", cupomCliente.getCliente().getId());
			
		} else {
			if(queryString.indexOf("WHERE") < 0) {
				queryString.append("WHERE ");
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.valor IS NULL AND cc.id IS NULL ");
		}
		
		Query query = entityManager.createQuery(queryString.toString());
		for(Entry<String, Object> entry: parametros.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		
		List resultList = query.getResultList();
		return resultList;
	}

}
