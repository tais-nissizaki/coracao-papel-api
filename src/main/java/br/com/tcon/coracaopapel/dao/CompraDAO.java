package br.com.tcon.coracaopapel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Repository
public class CompraDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		try {
			this.entityManager.persist(entidade);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
		return null;
	}

}
