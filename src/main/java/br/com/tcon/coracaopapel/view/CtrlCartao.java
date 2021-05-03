package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.endereco.ConsultarEnderecoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Controller
@RequestMapping("cartoes")
public class CtrlCartao {
	
	@Autowired
	private ConsultarEnderecoCommand consultarEnderecoCommand;
	
	
	@GetMapping(path = "cliente/{idCliente}")
	@ResponseBody
	public List<EntidadeDominio> obterCartoesCliente(
			@PathVariable("idCliente") Integer idCliente
			) {
		Cartao cartao = new Cartao();
		cartao.setCliente(new Cliente());
		cartao.getCliente().setId(idCliente);
		return (List<EntidadeDominio>)consultarEnderecoCommand.executar(cartao);
	}
}
