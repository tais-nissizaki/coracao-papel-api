package br.com.tcon.coracaopapel.negocio.cliente;

import java.util.Iterator;

import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class RemoverEnderecoInativoStrategy implements IStrategy {

	@Override
	public String processar(EntidadeDominio entidade) {
		Cliente cliente = (Cliente) entidade;
		Iterator<Endereco> iterator = cliente.getEnderecos().iterator();
		while (iterator.hasNext()) {
			Endereco enderecoBD = iterator.next();
			if(enderecoBD.getAtivo() != null && !enderecoBD.getAtivo()) {
				iterator.remove();
			}
		}
		return null;
	}

}
