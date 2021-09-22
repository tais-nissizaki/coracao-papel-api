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

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;

@Repository
public class UsuarioDAO implements IDAO {

	@Autowired
	protected EntityManager entityManager;

	@Override
	public boolean salvar(EntidadeDominio entidade) {
		return false;
	}

	@Override
	@Transactional
	public boolean alterar(EntidadeDominio entidade) {
		Usuario usuario = (Usuario) entidade;
		StringBuilder updateQuery = new StringBuilder("UPDATE Usuario u ");
		Map<String, Object> parametros = new HashMap<>();
		boolean adicionouCampoAlterado = false;
		if (!usuario.getSenhaAntiga().isBlank() && 
				!usuario.getConfirmacaoSenha().isBlank() && 
				usuario.getConfirmacaoSenha().equals(usuario.getSenha())) {
			if (!adicionouCampoAlterado) {
				updateQuery.append("SET ");
			} else { 
				updateQuery.append(", ");
			}
			updateQuery.append("u.senha = :senha");
			parametros.put("senha", usuario.getSenha());
		}
		
		updateQuery.append(" WHERE u.id = :idUsuario");
		parametros.put("idUsuario", usuario.getId());
		
		Query query = this.entityManager.createQuery(updateQuery.toString());
		for (Entry<String, Object> parametro: parametros.entrySet()) {
			query.setParameter(parametro.getKey(), parametro.getValue());
		}
		int registrosAtualizados = query.executeUpdate();
		return registrosAtualizados > 0;
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
