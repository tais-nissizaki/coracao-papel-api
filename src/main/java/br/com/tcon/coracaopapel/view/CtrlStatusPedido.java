package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.status_pedido.ConsultarStatusPedidoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.StatusPedido;

@Controller
@RequestMapping("status-pedido")
public class CtrlStatusPedido {

	@Autowired
	private ConsultarStatusPedidoCommand consultarStatuPedidoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosTiposCartao() {
		return consultarStatuPedidoCommand.executar(new StatusPedido());
	}
}
