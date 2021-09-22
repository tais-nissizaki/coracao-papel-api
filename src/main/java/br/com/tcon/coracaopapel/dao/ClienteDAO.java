package br.com.tcon.coracaopapel.dao;

import java.util.Date;
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

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Genero;
import br.com.tcon.coracaopapel.modelo.dominio.Telefone;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;

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
		cliente.setDocumentos(null);
		cliente.setEnderecos(null);

		entityManager.persist(cliente);
		if(documentos != null) { 
			for(int i=0; i< documentos.size(); i++) {
				Documento documento = documentos.get(i);
				documento.setCliente(cliente);
				entityManager.persist(documento);
			}
		}
		if(documentos != null) { 
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
		clienteBD.setGenero(entityManager.find(Genero.class, cliente.getGenero().getId()));
		clienteBD.setDataNascimento(cliente.getDataNascimento());
		clienteBD.setEmail(cliente.getEmail());
		
		if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
			Telefone telefoneBD = clienteBD.getTelefones().get(0);
			Telefone telefone = cliente.getTelefones().get(0);
			telefoneBD.setTipoTelefone(telefone.getTipoTelefone());
			telefoneBD.setNumero(telefone.getNumero());
			entityManager.merge(telefoneBD);
		}
		
		Iterator<Endereco> enderecoIterator = clienteBD.getEnderecos().iterator();
		while (enderecoIterator.hasNext()) {
			Endereco enderecoDB = enderecoIterator.next();
			if(!cliente.getEnderecos().contains(enderecoDB)) {				
				enderecoIterator.remove();
			}
		}
		
		enderecoIterator = cliente.getEnderecos().iterator();
		while (enderecoIterator.hasNext()) {
			Endereco endereco = enderecoIterator.next();
			if(!clienteBD.getEnderecos().contains(endereco)) {		
				endereco.setCliente(clienteBD);
				endereco.setDtCadastro(new Date());
				clienteBD.getEnderecos().add(endereco);
			} else {
				Endereco enderecoBD = entityManager.find(Endereco.class, endereco.getId());
				
				enderecoBD.setAtivo(true);
				enderecoBD.setBairro(endereco.getBairro());
				enderecoBD.setCep(endereco.getCep());
				enderecoBD.setCidade(endereco.getCidade());
				enderecoBD.setComplemento(endereco.getComplemento());
				enderecoBD.setIdentificadorEndereco(endereco.getIdentificadorEndereco());
				enderecoBD.setLogradouro(endereco.getLogradouro());
				enderecoBD.setNumero(endereco.getNumero());
				enderecoBD.setObservacao(endereco.getObservacao());
				enderecoBD.setTipoEndereco(endereco.getTipoEndereco());
				enderecoBD.setTipoLogradouro(endereco.getTipoLogradouro());
				enderecoBD.setTipoResidencia(endereco.getTipoResidencia());
			}
			
		}
		
		Iterator<Documento> documentosIterator = clienteBD.getDocumentos().iterator();
		while (documentosIterator.hasNext()) {
			Documento documentoDB = documentosIterator.next();
			if(!cliente.getDocumentos().contains(documentoDB)) {				
				documentosIterator.remove();
			}
			
		}
		
		documentosIterator = cliente.getDocumentos().iterator();
		while (documentosIterator.hasNext()) {
			Documento documento = documentosIterator.next();
			if(!clienteBD.getDocumentos().contains(documento)) {		
				documento.setCliente(clienteBD);
				documento.setDtCadastro(new Date());
				clienteBD.getDocumentos().add(documento);
			} else {
				Documento documentoBD = entityManager.find(Documento.class, documento.getId());
				documentoBD.setCodigo(documento.getCodigo());
				documentoBD.setValidade(documento.getValidade());
				documentoBD.setTipoDocumento(documento.getTipoDocumento());
			}
			
		}

		
		Iterator<Cartao> cartoesIterator = clienteBD.getCartoes().iterator();
		while (cartoesIterator.hasNext()) {
			Cartao cartaoDB = cartoesIterator.next();
			if(!cliente.getCartoes().contains(cartaoDB)) {				
				cartoesIterator.remove();
			}
			
		}
		
		cartoesIterator = cliente.getCartoes().iterator();
		while (cartoesIterator.hasNext()) {
			Cartao cartao = cartoesIterator.next();
			if(!clienteBD.getCartoes().contains(cartao)) {		
				cartao.setCliente(clienteBD);
				cartao.setDtCadastro(new Date());
				clienteBD.getCartoes().add(cartao);
			} else {
				Cartao cartaoBD = entityManager.find(Cartao.class, cartao.getId());
				
				cartaoBD.setBandeiraCartao(cartao.getBandeiraCartao());
				cartaoBD.setCvv(cartao.getCvv());
				cartaoBD.setDataValidade(cartao.getDataValidade());
				cartaoBD.setNomeImpresso(cartao.getNomeImpresso());
				cartaoBD.setNumero(cartao.getNumero());
				cartaoBD.setTipoCartao(cartao.getTipoCartao());
			}
			
		}
		
		entityManager.merge(clienteBD);
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
		
		if(cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
			if(!adicionouClausulaWhere) {
				queryString.append(" WHERE ");
				adicionouClausulaWhere = true;
			} else {
				queryString.append(" AND ");
			}
			queryString.append("UPPER(c.email) like :email");
			valoresFiltro.put("email", "%"+cliente.getEmail().toUpperCase()+"%");
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
//			for(int j=0; j< documentos.size(); j++) {
//				documentos.get(j).setCliente(null);
//				documentos.get(j).getTipoDocumento().setDocumentos(null);
//			}
			
//			List<Endereco> enderecos = clienteResultado.getEnderecos();
//			for(int j=0; j< enderecos.size(); j++) {
////				enderecos.get(j).setCliente(null);
////				enderecos.get(j).getCidade().setEndereco(null);
////				enderecos.get(j).getCidade().getEstado().setCidades(null); 
////				enderecos.get(j).getTipoEndereco().setEndereco(null);
//			}
		}
		if(resultado != null && !resultado.isEmpty()) {
			resultado = entityManager.createQuery(
						    "SELECT DISTINCT c " +
						    "FROM Cliente c " +
						    "JOIN FETCH c.documentos d " +
						    "where d.cliente in :clientes ", Cliente.class)
						.setParameter("clientes", resultado)
						.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
						.getResultList();
		}
		if(resultado != null && !resultado.isEmpty()) {
			resultado = entityManager.createQuery(
						    "SELECT DISTINCT c " +
						    "FROM Cliente c " +
						    "JOIN FETCH c.enderecos e " +
						    "where e.cliente in :clientes ", Cliente.class)
						.setParameter("clientes", resultado)
						.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
						.getResultList();
		}
		return resultado;
	}

}
