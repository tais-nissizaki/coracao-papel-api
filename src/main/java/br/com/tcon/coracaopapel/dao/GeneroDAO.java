package br.com.tcon.coracaopapel.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Genero;

@Repository
public class GeneroDAO implements IDAO {

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
		if (entidade != null) {
			Genero genero = (Genero) entidade;
			String queryString = montarQuery(genero);
			Query query = entityManager.createQuery(queryString);
			
			Map<String, Object> parametros = montarParametros(genero);
			Iterator<String> chaves = parametros.keySet().iterator();
			while(chaves.hasNext()) {
				String chave = chaves.next();
				query.setParameter(chave, parametros.get(chave));
			}
			return (List<EntidadeDominio>) query.getResultList();
		}
		return null;
	}
	
	private String montarQuery(Genero genero) {
		StringBuilder queryBase = new StringBuilder("SELECT g FROM Genero g ");
		if(genero.getId() != null) {
			queryBase.append("g.id = :id");
		} else if (genero.getDescricao() != null && !genero.getDescricao().isBlank()){
			queryBase.append("lower(g.descricao) like :descricao");
		}
		queryBase.append("ORDER BY g.descricao");
		return queryBase.toString();
	}
	
	private Map<String, Object> montarParametros(Genero genero) {
		Map<String, Object> parametros = new HashMap<>();
		if(genero.getId() != null) {
			parametros.put("id", genero.getId());
		} else if (genero.getDescricao() != null && !genero.getDescricao().isBlank()){
			parametros.put("descricao", genero.getDescricao().toLowerCase());
		}
		return parametros;
	}

}
