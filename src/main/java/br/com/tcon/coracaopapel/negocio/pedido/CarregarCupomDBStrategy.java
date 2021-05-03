package br.com.tcon.coracaopapel.negocio.pedido;

import java.util.ArrayList;
import java.util.List;

import br.com.tcon.coracaopapel.dao.CupomDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Cupom;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class CarregarCupomDBStrategy implements IStrategy {
	
	private CupomDAO cupomDAO;
	
	public CarregarCupomDBStrategy(CupomDAO cupomDAO) {
		this.cupomDAO = cupomDAO;
	}

	@Override
	public String processar(EntidadeDominio entidade) {
		Pedido pedido = (Pedido) entidade;
//		if(pedido.getCupons() != null && !pedido.getCupons().isEmpty()) {
//			List<Cupom> cupons = new ArrayList<>();
//			for(Cupom cupom: pedido.getCupons()) {
//				if(cupom.getId() != null) {
//					List<EntidadeDominio> consultar = cupomDAO.consultar(cupom);
//					if(consultar != null && !consultar.isEmpty()) {
//						for(EntidadeDominio entidadeDominio: consultar) {
//							cupons.add((Cupom)entidadeDominio);
//						}
//					}
//				}
//			}
//			pedido.setCupons(cupons);
//		}
		return null;
	}

}
