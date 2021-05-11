package br.com.tcon.coracaopapel.dao;

import java.math.BigDecimal;
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
import br.com.tcon.coracaopapel.modelo.dominio.GrupoPrecificacao;

@Repository
public class GrupoPrecificacaoDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
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
		if(entidade.getId() != null && entidade.getId().compareTo(0) > 0) {
			GrupoPrecificacao grupoPrecificacao = this.entityManager.find(GrupoPrecificacao.class, entidade.getId());
			List<EntidadeDominio> resultados = new ArrayList<>();
			resultados.add(grupoPrecificacao);
			return resultados;
		}
		GrupoPrecificacao grupoPrecificacao = (GrupoPrecificacao) entidade;
		StringBuilder queryStatement = new StringBuilder("SELECT g FROM GrupoPrecificacao g ");
		Map<String, Object> parametros = new  HashMap<>();
		boolean adicionouWhere = false;
		if(grupoPrecificacao.getDescricao() != null && !grupoPrecificacao.getDescricao().isBlank()) {
			if(adicionouWhere) {
				queryStatement.append(" AND ");
			} else {
				queryStatement.append(" WHERE ");
			}
			queryStatement.append(" g.descricao = :descricao ");
			parametros.put("descricao", "%" + grupoPrecificacao.getDescricao() + "%");
		}
		
		if(grupoPrecificacao.getNome() != null && !grupoPrecificacao.getNome().isBlank()) {
			if(adicionouWhere) {
				queryStatement.append(" AND ");
			} else {
				queryStatement.append(" WHERE ");
			}
			queryStatement.append(" g.nome = :nome ");
			parametros.put("nome", "%" + grupoPrecificacao.getNome() + "%");
		}

		
		if(grupoPrecificacao.getPercentual() != null && grupoPrecificacao.getPercentual().compareTo(BigDecimal.ZERO) > 0) {
			if(adicionouWhere) {
				queryStatement.append(" AND ");
			} else {
				queryStatement.append(" WHERE ");
			}
			queryStatement.append(" g.percentual = :percentual ");
			parametros.put("percentual", grupoPrecificacao.getNome());
		}
		
		Query query = this.entityManager.createQuery(queryStatement.toString());
		
		if(!parametros.isEmpty()) {
			for(Entry<String, Object> parametro: parametros.entrySet()) {
				query.setParameter(parametro.getKey(), parametro.getValue());
			}
		}
		
		return query.getResultList();
	}

}
