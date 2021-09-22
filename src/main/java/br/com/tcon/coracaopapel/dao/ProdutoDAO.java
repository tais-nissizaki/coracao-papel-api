package br.com.tcon.coracaopapel.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.tcon.coracaopapel.modelo.dominio.Categoria;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
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
	@Transactional
	public boolean alterar(EntidadeDominio entidade) {
		try {
			this.entityManager.flush();
			Produto produto = (Produto) entidade;
			StringBuilder updateCommand = new StringBuilder("UPDATE Produto p SET ");
			Map<String, Object> parametros = new HashMap<>();
			boolean adicionarVirgula = false;
			if (produto.getAnoEdicao() != null) {
				adicionarVirgula = true;
				updateCommand.append(" p.anoEdicao = :anoEdicao ");
				parametros.put("anoEdicao", produto.getAnoEdicao());
			}
			if (produto.getAutor() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.autor = :autor ");
				parametros.put("autor", produto.getAutor());
			}
			if (produto.getCodigoBarras() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.codigoBarras = :codigoBarras ");
				parametros.put("codigoBarras", produto.getCodigoBarras());
			}
			if (produto.getEdicao() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.edicao = :edicao ");
				parametros.put("edicao", produto.getEdicao());
			}
			if (produto.getEditora() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.editora = :editora ");
				parametros.put("editora", produto.getEditora());
			}
			if (produto.getIsbn() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.isbn = :isbn ");
				parametros.put("isbn", produto.getIsbn());
			}
			if (produto.getNumeroPaginas() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.numeroPaginas = :numeroPaginas ");
				parametros.put("numeroPaginas", produto.getNumeroPaginas());
			}
			if (produto.getQuantidadeEstoque() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.quantidadeEstoque = :quantidadeEstoque ");
				parametros.put("quantidadeEstoque", produto.getQuantidadeEstoque());
			}
			if (produto.getSinopse() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.sinopse = :sinopse ");
				parametros.put("sinopse", produto.getSinopse());
			}
			if (produto.getTitulo() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.titulo = :titulo ");
				parametros.put("titulo", produto.getTitulo());
			}
			if (produto.getValor() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.valor = :valor ");
				parametros.put("valor", produto.getValor());
			}

			if (produto.getImageBase64() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.imagem = :imagem ");
				parametros.put("imagem", produto.getImagem());
			}

			if (produto.getGrupoPrecificacao() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.grupoPrecificacao = :grupoPrecificacao ");
				parametros.put("grupoPrecificacao", produto.getGrupoPrecificacao());
			}

//			if (produto.getCategorias() != null) {
//				if(adicionarVirgula) {
//					updateCommand.append(",");
//				}
//				adicionarVirgula = true;
//				updateCommand.append(" p.categorias = :categorias ");
//				parametros.put("categorias", produto.getCategorias());
//			}

			if (produto.getDimensao() != null) {
				if(adicionarVirgula) {
					updateCommand.append(",");
				}
				adicionarVirgula = true;
				updateCommand.append(" p.dimensao = :dimensao ");
				parametros.put("dimensao", produto.getDimensao());
			}
			updateCommand.append(" WHERE p.id = :id ");
			parametros.put("id", produto.getId());
			
			Query query = entityManager.createQuery(updateCommand.toString());
			for(Entry<String, Object> parametro: parametros.entrySet()) {
				query.setParameter(parametro.getKey(), parametro.getValue());
			}
			
			int updated = query.executeUpdate();
			if(updated <= 0) {
				throw new Exception("Falha ao atuaizar produto");
			}
			
			if(produto.getCategorias() != null && !produto.getCategorias() .isEmpty()) {
				Produto produtoDB = entityManager.find(Produto.class, entidade.getId());
				
				Iterator<Categoria> categoriaIterator = produtoDB.getCategorias().iterator();
				while (categoriaIterator.hasNext()) {
					Categoria categoriaDB = categoriaIterator.next();
					if(!produto.getCategorias().contains(categoriaDB)) {				
						categoriaIterator.remove();
					}
				}
				
				categoriaIterator = produto.getCategorias().iterator();
				while (categoriaIterator.hasNext()) {
					Categoria categoria = categoriaIterator.next();
					if(!produtoDB.getCategorias().contains(categoria)) {
						produtoDB.getCategorias().add(categoria);
					}
				}
				entityManager.merge(produtoDB);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean inativar(EntidadeDominio entidade) {
		Produto produto = entityManager.find(Produto.class, entidade.getId());
		produto.setAtivo(false);
		entityManager.merge(produto);
		return true;
	}

	@Override
	@Transactional
	public boolean ativar(EntidadeDominio entidade) {
		Produto produto = entityManager.find(Produto.class, entidade.getId());
		produto.setAtivo(true);
		entityManager.merge(produto);
		return true;
	}

	@Override
	public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
		Produto produto = (Produto) entidade;
		if(produto != null) {
			if(produto.getId() != null) {
				Produto produtoBD = entityManager.find(Produto.class, produto.getId());
				List<EntidadeDominio> produtos = new ArrayList<>();
				produtos.add(produtoBD);
				return produtos;
			}
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
			
			if(produto.getAutor() != null && !produto.getAutor().isBlank()) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" UPPER(p.autor) like :autor ");
				params.put("autor", "%"+produto.getAutor().toUpperCase()+"%");
			}
			
			if(produto.getIsbn() != null && !produto.getIsbn().isBlank()) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" p.isbn = :isbn ");
				params.put("isbn", produto.getIsbn());
			}
			
			if(produto.getEditora() != null && !produto.getEditora().isBlank()) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" UPPER(p.editora) like :editora ");
				params.put("editora", "%"+produto.getEditora().toUpperCase()+"%");
			}
			
			if(produto.getCodigoBarras() != null && !produto.getCodigoBarras().isBlank()) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" p.codigoBarras = :codigoBarras ");
				params.put("codigoBarras", produto.getCodigoBarras());
			}

			if(produto.getAtivo() != null) {
				if (!queryString.toString().contains("WHERE")) {
					queryString.append(" WHERE ");
				} else {
					queryString.append(" AND ");
				}
				queryString.append(" p.ativo = :ativo");
				params.put("ativo", produto.getAtivo());
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
