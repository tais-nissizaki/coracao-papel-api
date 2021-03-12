package br.com.tcon.coracaopapel.negocio.cliente;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class DefinirTipoClienteNovoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		if ((entidade.getId() == null || entidade.getId() <= 0) && entidade instanceof Cliente) {
			Cliente cliente = (Cliente) entidade;
			TipoCliente tipoCliente = new TipoCliente();
			tipoCliente.setId(1); //ID 1 == Cliente Bronze na base de dados
			cliente.setTipoCliente(tipoCliente);
		}
		return null;
	}

}
