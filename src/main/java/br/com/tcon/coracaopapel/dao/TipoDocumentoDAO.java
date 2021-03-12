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
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;

@Repository
public class TipoDocumentoDAO implements IDAO {

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

		List<TipoDocumento> listaResultadoQuery = (List<TipoDocumento>) query.getResultList();
		List<EntidadeDominio> listaRetorno = new ArrayList<>();
		for(int i=0; i< listaResultadoQuery.size(); i++) {
			TipoDocumento tipoDocumento = (TipoDocumento) listaResultadoQuery.get(i);
			entityManager.detach(tipoDocumento);
//			tipoDocumento.setDocumentos(null);
			listaRetorno.add(tipoDocumento);
		}
		
		return listaRetorno;
	}
	
	private String montarQuery(EntidadeDominio entidade) {
		StringBuilder queryBase = new StringBuilder("SELECT t FROM TipoDocumento t ");
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
