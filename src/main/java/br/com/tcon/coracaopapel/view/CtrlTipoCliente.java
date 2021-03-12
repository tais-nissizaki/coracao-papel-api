package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.tipo_cliente.ConsultarTipoClienteCommand;
import br.com.tcon.coracaopapel.modelo.dominio.TipoCliente;

@Controller
@RequestMapping("tipos-cliente")
public class CtrlTipoCliente {

	@Autowired
	private ConsultarTipoClienteCommand consultarTipoClienteCommand;

	@GetMapping
	@ResponseBody
	public List<TipoCliente> obterTodosTiposDocumento() {
		return (List<TipoCliente>) consultarTipoClienteCommand.executar(new TipoCliente());
	}
}
