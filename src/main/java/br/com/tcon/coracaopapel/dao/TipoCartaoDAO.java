package br.com.tcon.coracaopapel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCartao;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;

@Repository
public class TipoCartaoDAO implements IDAO {

	@Autowired
	protected EntityManager entityManager;

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
//
//		List<TipoCartao> listaResultadoQuery = (List<TipoCartao>) query.getResultList();
//		List<EntidadeDominio> listaRetorno = new ArrayList<>();
//		for(int i=0; i< listaResultadoQuery.size(); i++) {
//			TipoCartao tipoEndereco = (TipoCartao) listaResultadoQuery.get(i);
//			entityManager.detach(tipoEndereco);
//			listaRetorno.add(tipoEndereco);
//		}
		
		return (List<EntidadeDominio>) query.getResultList();
	}
	
	private String montarQuery(EntidadeDominio entidade) {
		StringBuilder queryBase = new StringBuilder("SELECT t FROM TipoCartao t ");
		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				queryBase.append("WHERE t.id = :id ");
			}
		}
		return queryBase.toString();
	}

	private Map<String, Object> montarParametros(EntidadeDominio entidade) {
		Map<String, Object> parametrosQuery = new HashMap<>();
		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				parametrosQuery.put("id", entidade.getId());
			}
		}
		return parametrosQuery;
	}

}
