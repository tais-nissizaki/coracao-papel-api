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

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class EnderecoDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		Endereco endereco = (Endereco) entidade;
		try {
			Cliente cliente = entityManager.find(Cliente.class, endereco.getCliente().getId());
			cliente.getEnderecos().add(endereco);
			entityManager.merge(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean alterar(EntidadeDominio entidade) {
		try {
			entityManager.merge(entidade);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean inativar(EntidadeDominio entidade) {
		try {
			Endereco endereco = entityManager.find(Endereco.class, entidade.getId());
			endereco.setAtivo(false);
			entityManager.merge(endereco);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean ativar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		if(entidade == null) {
			return null;
		}
		
		if (entidade.getId() != null) {
			return List.of(entityManager.find(Endereco.class, entidade.getId()));
		}
		
		Endereco endreco = (Endereco) entidade;
		StringBuilder queryString = new StringBuilder("SELECT e FROM Endereco e JOIN e.cliente c ");
		Map<String, Object> parametros = new HashMap<>();
		boolean adicionouWhere = false;
		if(endreco.getCliente() != null && endreco.getCliente().getId() != null) {
			if(!adicionouWhere) {
				queryString.append("WHERE ");
				adicionouWhere = true;
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.id = :idCliente ");
			parametros.put("idCliente", endreco.getCliente().getId());
		}
		
		if(endreco.getTipoEndereco() != null && endreco.getTipoEndereco().getId() != null) {
			if(!adicionouWhere) {
				queryString.append("WHERE ");
				adicionouWhere = true;
			} else {
				queryString.append("AND ");
			}
			queryString.append("e.tipoEndereco.id = :idTipoEndereco ");
			parametros.put("idTipoEndereco", endreco.getTipoEndereco().getId());
		}
		
		Query query = entityManager.createQuery(queryString.toString());
		for(Entry<String, Object> entry: parametros.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

}
