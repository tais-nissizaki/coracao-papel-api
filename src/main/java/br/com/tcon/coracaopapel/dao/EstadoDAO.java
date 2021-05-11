package br.com.tcon.coracaopapel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;

@Repository
public class EstadoDAO implements IDAO {

	@Autowired
	protected EntityManager entityManager;

	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		entityManager.persist(entidade);
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
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		String queryString =  montarQuery(entidade);
		Map<String, Object> parametros = montarParametros(entidade);
		
		Query query = entityManager.createQuery(queryString);
		if(!parametros.isEmpty()) {
			Iterator<String> chaves = parametros.keySet().iterator();
			while(chaves.hasNext()) {
				String chave = chaves.next();
				query.setParameter(chave, parametros.get(chave));
				
			}
		}

		List<Estado> listaResultadoQuery = (List<Estado>) query.getResultList();
		List<EntidadeDominio> listaRetorno = new ArrayList<>();
		for(int i=0; i< listaResultadoQuery.size(); i++) {
			Estado estado = (Estado) listaResultadoQuery.get(i);
//			estado.getCidades();
			entityManager.detach(estado);
//			estado.setCidades(null);
			listaRetorno.add(estado);
		}
		
		return listaRetorno;
	}
	
	private String montarQuery(EntidadeDominio entidade) {
		StringBuilder queryBase = new StringBuilder("SELECT e FROM Estado e ");
		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				queryBase.append("WHERE e.id = :id ");
			} else {
				Estado estadoFiltro = (Estado) entidade;
				if(estadoFiltro.getDescricao() != null && !estadoFiltro.getDescricao().isBlank()) {
					queryBase.append("WHERE e.sigla = :sigla ");
				}
			}
		}
		queryBase.append("ORDER BY e.descricao");
		return queryBase.toString();
	}

	private Map<String, Object> montarParametros(EntidadeDominio entidade) {
		Map<String, Object> parametrosQuery = new HashMap<>();

		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				parametrosQuery.put("id", entidade.getId());
			} else {
				Estado estadoFiltro = (Estado) entidade;
				if(estadoFiltro.getDescricao() != null && !estadoFiltro.getDescricao().isBlank()) {
					parametrosQuery.put("sigla", estadoFiltro.getDescricao());
				}
			}
		}
		return parametrosQuery;
	}
	
}
