package br.com.tcon.coracaopapel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pais;

@Repository
public class PaisDAO implements IDAO {
	
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
		if ( entidade.getId() != null) {
			List<EntidadeDominio> paises = new ArrayList<>();
			paises.add(this.entityManager.find(Pais.class, entidade.getId()));
			return paises;
		}
		Pais pais = (Pais) entidade;
		StringBuilder queryString = new StringBuilder("SELECT p FROM Pais p ");
		Map<String, Object> params = new HashMap<>();
		if(pais.getDescricao() != null && !pais.getDescricao().isBlank()) {
			queryString.append(" WHERE UPPER(p.descricao) like :pais ");
			params.put("pais", "%"+pais.getDescricao().toUpperCase()+"%");
		}
		Query query = entityManager.createQuery(queryString.toString());
		for(Entry<String, Object> parametro: params.entrySet()) {
			query.setParameter(parametro.getKey(), parametro.getValue());
		}
		return query.getResultList();
	}

}
