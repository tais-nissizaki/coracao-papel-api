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

import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.TransacaoPedido;

@Repository
public class PedidoDAO implements IDAO {
	
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
			Pedido pedido = (Pedido)entidade;
			Pedido pedidoBD = this.entityManager.find(Pedido.class, entidade.getId());
			if(pedido.getStatusPedido().getId() != pedidoBD.getStatusPedido().getId()) {
				Query query = this.entityManager.createQuery("UPDATE Pedido p SET p.idStatusPedido = :idStatusPedido where p.id = :idPedido")
					.setParameter("idStatusPedido", pedido.getStatusPedido().getId())
					.setParameter("idPedido", pedido.getId());
				query.executeUpdate();
				this.entityManager.flush();
			}
			if(pedido.isTrocaSolicitada() != pedidoBD.isTrocaSolicitada()) {
				Query query = this.entityManager.createQuery("UPDATE Pedido p SET p.trocaSolicitada = :trocaSolicitada where p.id = :idPedido")
					.setParameter("trocaSolicitada", pedido.isTrocaSolicitada())
					.setParameter("idPedido", pedido.getId());
				query.executeUpdate();
				this.entityManager.flush();
			}
			
			for(TransacaoPedido transacaoPedido: pedido.getTransacoesPedido()) {
				transacaoPedido.setPedido(pedidoBD);
				this.entityManager.persist(transacaoPedido);
				this.entityManager.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
		if(entidade != null && entidade.getId() != null && entidade.getId() > 0) {
			Pedido statusPedido = entityManager.find(Pedido.class, entidade.getId());
			List<EntidadeDominio> retorno = new ArrayList<>();
			retorno.add(statusPedido);
			return retorno;
		}
		
		StringBuilder queryString = new StringBuilder("SELECT p FROM Pedido p ");
		Map<String, Object> parametros = new HashMap<>();
		Pedido pedido = (Pedido) entidade;
		if(pedido.getStatusPedido() != null && pedido.getStatusPedido().getId() != null ) {
			if(queryString.toString().contains("WHERE")) {
				queryString.append(" AND ");
			} else {
				queryString.append(" WHERE ");
			}
			queryString.append(" p.statusPedido.id = :idStatusPedido ");
			parametros.put("idStatusPedido", pedido.getStatusPedido().getId());
		}
		
		if(pedido.getInicioFiltro() != null && pedido.getFimFiltro() != null) {
			if(queryString.toString().contains("WHERE")) {
				queryString.append(" AND ");
			} else {
				queryString.append(" WHERE ");
			}
			queryString.append(" (p.dtCadastro >= :inicioFiltro ");
			queryString.append(" AND p.dtCadastro <= :fimFiltro) ");
			parametros.put("inicioFiltro", pedido.getInicioFiltro());
			parametros.put("fimFiltro", pedido.getFimFiltro());
		}

		
		if(pedido.getCliente() != null && pedido.getCliente().getId() != null) {
			if(queryString.toString().contains("WHERE")) {
				queryString.append(" AND ");
			} else {
				queryString.append(" WHERE ");
			}

			queryString.append(" p.cliente.id = :idCliente ");
			parametros.put("idCliente", pedido.getCliente().getId());
		}
		Query query = entityManager.createQuery(queryString.toString());
		for(Entry<String, Object> itemParametro: parametros.entrySet()) {
			query.setParameter(itemParametro.getKey(), itemParametro.getValue());
		}
		
		return query.getResultList();
	}

}
