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

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CartaoDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		Cartao cartao = (Cartao) entidade;
		try {
			entityManager.merge(cartao);
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
			Cartao cartao = entityManager.find(Cartao.class, entidade.getId());
			cartao.setAtivo(false);
			entityManager.merge(cartao);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean ativar(EntidadeDominio entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		if (entidade.getId() != null) {
			return List.of(entityManager.find(Cartao.class, entidade.getId()));
		}
		Cartao cartao = (Cartao) entidade;
		StringBuilder queryString = new StringBuilder("SELECT c FROM Cartao c JOIN c.cliente cl ");
		Map<String, Object> parametros = new HashMap<>();
		boolean adicionouWhere = false;
		if (cartao.getCliente() != null && cartao.getCliente().getId() != null) {
			if (!adicionouWhere) {
				queryString.append("WHERE ");
				adicionouWhere = true;
			} else {
				queryString.append("AND ");
			}
			queryString.append("cl.id = :idCliente ");
			parametros.put("idCliente", cartao.getCliente().getId());
		}
		if (cartao.getAtivo() != null) {
			if (!adicionouWhere) {
				queryString.append("WHERE ");
				adicionouWhere = true;
			} else {
				queryString.append("AND ");
			}
			queryString.append("c.ativo = :ativo ");
			parametros.put("ativo", cartao.getAtivo());
		}
		
		
		Query query = entityManager.createQuery(queryString.toString());
		for (Entry<String, Object> entry: parametros.entrySet()) {
			query.setParameter(entry.getKey(),	entry.getValue());
		}
		
		List resultado = query.getResultList();
		desanexarDadosBD(resultado);
		return resultado;
	}
	

	private void desanexarDadosBD(List<Cartao> cartoes) {
		if (cartoes != null) {
			for (int i=0; i<cartoes.size(); i++) {
				desanexarDadosBD(cartoes.get(i));
			}
		}
	}
	
	private void desanexarDadosBD(Cartao cartao) {
		if(cartao != null) {
			if(cartao.getCliente() != null && cartao.getCliente().getId() != null) {
				entityManager.detach(cartao.getCliente());
				cartao.getCliente().setCarrinho(null);
				cartao.getCliente().setCartoes(null);
				cartao.getCliente().setCuponsCliente(null);
				cartao.getCliente().setDocumentos(null);
				cartao.getCliente().setEnderecos(null);
				cartao.getCliente().setGenero(null);
				cartao.getCliente().setTelefones(null);
				cartao.getCliente().setTipoCliente(null);
				cartao.getCliente().setUsuario(null);
			}
			entityManager.detach(cartao);
		}
	}

}
