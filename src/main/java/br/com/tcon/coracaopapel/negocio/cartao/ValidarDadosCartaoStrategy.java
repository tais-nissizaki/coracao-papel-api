package br.com.tcon.coracaopapel.negocio.cartao;

import java.util.Date;

import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarDadosCartaoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Cartao cartao = (Cartao) entidade;
		StringBuilder erros = new StringBuilder();
		if(cartao.getTipoCartao() == null || cartao.getTipoCartao().getId() == null) {
			erros.append("Tipo de cartão não informado.").append("\n");
		}
		if(cartao.getBandeiraCartao() == null || cartao.getBandeiraCartao().getId() == null) {
			erros.append("Bandeira não informada.").append("\n");
		}
		
		if(cartao.getNumero() == null || cartao.getNumero().isBlank()) {
			erros.append("Número do cartão não informado.").append("\n");
		}
		if(cartao.getNomeImpresso() == null || cartao.getNomeImpresso().isBlank()) {
			erros.append("Nome impresso no cartão não informado.").append("\n");
		}
		if(cartao.getCvv() == null || cartao.getCvv().isBlank()) {
			erros.append("CVV não informado.").append("\n");
		}		
		if(cartao.getDataValidade() == null) {
			erros.append("Data de validade do cartão não informada.").append("\n");
		} else if(cartao.getDataValidade().before(new Date())) {
			erros.append("Data de validade do cartão inválida.").append("\n");
		}
		
		return erros.toString();
	}

}
