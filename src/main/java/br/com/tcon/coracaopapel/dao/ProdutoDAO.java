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

import br.com.tcon.coracaopapel.modelo.dominio.Categoria;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@Repository
public class ProdutoDAO implements IDAO {
	
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
		Produto produto = (Produto) entidade;
		if(produto != null) {
			StringBuilder queryString = new StringBuilder("SELECT DISTINCT p FROM Produto p JOIN p.categorias c ");
			Map<String, Object> params = new HashMap<>();
			if(produto.getCategorias() != null && !produto.getCategorias().isEmpty()) {
				List<Integer> categoriaIds = new ArrayList<Integer>();
				for (Categoria categoria: produto.getCategorias()) {
					categoriaIds.add(categoria.getId());
				}
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" c.id in (:categoriaIds) ");
				params.put("categoriaIds", categoriaIds);
			}
			
			if(produto.getTitulo() != null && !produto.getTitulo().isBlank()) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" UPPER(p.titulo) like :titulo ");
				params.put("titulo", "%"+produto.getTitulo().toUpperCase()+"%");
			}
			
			Query query = entityManager.createQuery(queryString.toString());
			for (Entry<String, Object> parametro: params.entrySet()) {
				query.setParameter(parametro.getKey(), parametro.getValue());
			}
			
			return query.getResultList();
		}
		return null;
	}

}
