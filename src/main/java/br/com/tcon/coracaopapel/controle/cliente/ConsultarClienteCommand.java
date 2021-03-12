package br.com.tcon.coracaopapel.controle.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.tcon.coracaopapel.controle.ICommand;
import br.com.tcon.coracaopapel.controle.IFachada;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Component
public class ConsultarClienteCommand implements ICommand {
	
	@Autowired
	private IFachada fachada;

	@Override
	public Object executar(EntidadeDominio entidade) {
		return fachada.consultar(entidade);
	}

}
