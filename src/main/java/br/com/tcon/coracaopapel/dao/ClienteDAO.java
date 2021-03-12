package br.com.tcon.coracaopapel.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.Cidade;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;

@Repository
public class ClienteDAO implements IDAO {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public boolean salvar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		List<Documento> documentos = cliente.getDocumentos();
		List<Endereco> enderecos = cliente.getEnderecos();
		cliente.setEnderecos(null);
		cliente.setDocumentos(null);
		entityManager.persist(cliente);
		entityManager.flush();
		if(documentos != null) { 
			for(int i=0; i< documentos.size(); i++) {
				Documento documento = documentos.get(i);
				documento.setCliente(cliente);
				entityManager.persist(documento);
			}
		}
		if(enderecos != null) { 
			for(int i=0; i< enderecos.size(); i++) {
				Endereco endereco = enderecos.get(i);
				endereco.setCliente(cliente);
				entityManager.persist(endereco);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean alterar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		
		Cliente clienteBD = entityManager.find(Cliente.class, entidade.getId());
		clienteBD.setNome(cliente.getNome());
		clienteBD.setAtivo(cliente.getAtivo());
		clienteBD.setTipoCliente(entityManager.find(TipoCliente.class, cliente.getTipoCliente().getId()));

		for(int i=0; i< clienteBD.getEnderecos().size(); i++) {
			Endereco enderecoDB = clienteBD.getEnderecos().get(i);
			entityManager.remove(enderecoDB);
		}
		for(int i=0; i< clienteBD.getDocumentos().size(); i++) {
			Documento documentoDB = clienteBD.getDocumentos().get(i);
			entityManager.remove(documentoDB);
		}
		clienteBD.getEnderecos().clear();;
		clienteBD.getDocumentos().clear();;
		
		for(int i=0; i< cliente.getEnderecos().size(); i++) {
			Endereco endereco = cliente.getEnderecos().get(i);
			endereco.setCliente(clienteBD);
			if(endereco.getId() != null && endereco.getId().intValue() > 0) {
				entityManager.merge(endereco);
			} else {
				entityManager.persist(endereco);
			}
		}
		
		for(int i=0; i< cliente.getDocumentos().size(); i++) {
			Documento documento = cliente.getDocumentos().get(i);
			documento.setCliente(clienteBD);
			if(documento.getId() != null && documento.getId().intValue() > 0) {
				entityManager.merge(documento);
			} else {
				entityManager.persist(documento);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean inativar(EntidadeDominio entidade) {
		Cliente cliente = entityManager.find(Cliente.class, entidade.getId());
		cliente.setAtivo(false);
		entityManager.merge(cliente);
		return true;
	}

	@Override
	@Transactional
	public boolean ativar(EntidadeDominio entidade) {
		Cliente cliente = entityManager.find(Cliente.class, entidade.getId());
		cliente.setAtivo(true);
		entityManager.merge(cliente);
		return true;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		StringBuilder queryString = new StringBuilder("SELECT DISTINCT c FROM Cliente c JOIN c.documentos d ");
		Map<String, Object> valoresFiltro = new HashMap<>();
		boolean adicionouClausulaWhere = false;
		
		if(cliente.getId() != null && cliente.getId() > 0) {
			if(!adicionouClausulaWhere) {
				queryString.append(" WHERE ");
				adicionouClausulaWhere = true;
			} else {
				queryString.append(" AND ");
			}
			queryString.append("c.id = :id");
			valoresFiltro.put("id", cliente.getId());
		}
		
		if(cliente.getNome() != null && !cliente.getNome().isBlank()) {
			if(!adicionouClausulaWhere) {
				queryString.append(" WHERE ");
				adicionouClausulaWhere = true;
			} else {
				queryString.append(" AND ");
			}
			queryString.append("UPPER(c.nome) like :nome");
			valoresFiltro.put("nome", "%"+cliente.getNome().toUpperCase()+"%");
		}
		
		//Por Documento
		if(cliente.getDocumentos() != null && !cliente.getDocumentos().isEmpty()) {
			if(!adicionouClausulaWhere) {
				queryString.append(" WHERE ");
				adicionouClausulaWhere = true;
			} else {
				queryString.append(" AND ");
			}
			for(int i=0; i<cliente.getDocumentos().size(); i++) {
				queryString.append("d.tipoDocumento.id = :idTipoDocumento ");
				queryString.append(" AND d.codigo = :codigoDocumento");
				valoresFiltro.put("idTipoDocumento", cliente.getDocumentos().get(i).getTipoDocumento().getId());
				valoresFiltro.put("codigoDocumento", cliente.getDocumentos().get(i).getCodigo());
				if( i+1 < cliente.getDocumentos().size()) {
					queryString.append(" AND ");
				}
			}
		}
		
		Query query = entityManager.createQuery(queryString.toString()).setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
		if(!valoresFiltro.isEmpty()) {
			Iterator<String> chaves = valoresFiltro.keySet().iterator();
			while(chaves.hasNext()) {
				String chave = chaves.next();
				query.setParameter(chave, valoresFiltro.get(chave));
			}
		}
		List resultado = query.getResultList();
		for(int i=0; i< resultado.size(); i++) {
			Cliente clienteResultado = (Cliente) resultado.get(i);
			List<Documento> documentos = clienteResultado.getDocumentos();
			for(int j=0; j< documentos.size(); j++) {
				documentos.get(j).setCliente(null);
//				documentos.get(j).getTipoDocumento().setDocumentos(null);
			}
			
			List<Endereco> enderecos = clienteResultado.getEnderecos();
			for(int j=0; j< enderecos.size(); j++) {
				enderecos.get(j).setCliente(null);
//				enderecos.get(j).getCidade().setEndereco(null);
//				enderecos.get(j).getCidade().getEstado().setCidades(null); 
//				enderecos.get(j).getTipoEndereco().setEndereco(null);
			}
		}
		if(resultado != null && !resultado.isEmpty()) {
			resultado = entityManager.createQuery(
						    "SELECT DISTINCT c " +
						    "FROM Cliente c " +
						    "JOIN FETCH c.documentos d " +
						    "where c in :clientes ", Cliente.class)
						.setParameter("clientes", resultado)
						.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
						.getResultList();
		}
		if(resultado != null && !resultado.isEmpty()) {
			resultado = entityManager.createQuery(
						    "SELECT DISTINCT c " +
						    "FROM Cliente c " +
						    "JOIN FETCH c.enderecos e " +
						    "where c in :clientes ", Cliente.class)
						.setParameter("clientes", resultado)
						.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
						.getResultList();
		}
		return resultado;
	}

}
