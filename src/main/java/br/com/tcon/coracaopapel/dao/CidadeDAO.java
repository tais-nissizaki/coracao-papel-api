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

import br.com.tcon.coracaopapel.modelo.dominio.Cidade;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CidadeDAO implements IDAO {

	@Autowired
	protected EntityManager entityManager;
	
	public boolean salvar(EntidadeDominio entidade) {
		entityManager.persist(entidade);
		return true;
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

		List<Cidade> listaResultadoQuery = (List<Cidade>) query.getResultList();
		List<EntidadeDominio> listaRetorno = new ArrayList<>();
		for(int i=0; i< listaResultadoQuery.size(); i++) {
			Cidade cidade = (Cidade) listaResultadoQuery.get(i);
			cidade.getEstado();
			entityManager.detach(cidade);
//			cidade.getEstado().setCidades(null);
//			cidade.setEndereco(null);
			listaRetorno.add(cidade);
		}
		
		return listaRetorno;
	}
	

	
	private String montarQuery(EntidadeDominio entidade) {
		StringBuilder queryBase = new StringBuilder("SELECT c FROM Cidade c ");
		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				queryBase.append("WHERE c.id = :id ");
			} else {
				Cidade cidadeFiltro = (Cidade) entidade;
				if(cidadeFiltro.getEstado() != null && cidadeFiltro.getEstado().getDescricao() != null && !cidadeFiltro.getEstado().getDescricao().isBlank()) {
					queryBase.append("WHERE c.estado.descricao = :descricao ");
				}
			}
		}
		return queryBase.toString();
	}

	private Map<String, Object> montarParametros(EntidadeDominio entidade) {
		Map<String, Object> parametrosQuery = new HashMap<>();

		if (entidade != null) {
			if(entidade.getId() != null && entidade.getId() > 0) {
				parametrosQuery.put("id", entidade.getId());
			} else {
				Cidade cidadeFiltro = (Cidade) entidade;
				if(cidadeFiltro.getEstado() != null && cidadeFiltro.getEstado().getDescricao() != null && !cidadeFiltro.getEstado().getDescricao().isBlank()) {
					parametrosQuery.put("descricao", cidadeFiltro.getEstado().getDescricao());
				}
			}
		}
		return parametrosQuery;
	}
}
