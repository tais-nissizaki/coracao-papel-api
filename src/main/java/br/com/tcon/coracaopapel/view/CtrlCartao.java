package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.cartao.AlterarCartaoCommand;
import br.com.tcon.coracaopapel.controle.cartao.ConsultarCartaoCommand;
import br.com.tcon.coracaopapel.controle.cartao.InativarCartaoCommand;
import br.com.tcon.coracaopapel.controle.cartao.SalvarCartaoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;

@Controller
@RequestMapping("cartoes")
public class CtrlCartao {
	
	@Autowired
	private ConsultarCartaoCommand consultarCartaoCommand;

	@Autowired
	private SalvarCartaoCommand salvarCartaoCommand;
	
	@Autowired
	private AlterarCartaoCommand alterarCartaoCommand;
	
	@Autowired
	private InativarCartaoCommand inativarCartaoCommand;
	
	@GetMapping(path = "cliente/{idCliente}")
	@ResponseBody
	public List<EntidadeDominio> obterCartoesCliente(
			@PathVariable("idCliente") Integer idCliente
			) {
		Cartao cartao = new Cartao();
		cartao.setCliente(new Cliente());
		cartao.setAtivo(true);
		cartao.getCliente().setId(idCliente);
		return (List<EntidadeDominio>)consultarCartaoCommand.executar(cartao);
	}
	
	@PostMapping(path = "/cliente/{idCliente}")
	@ResponseBody
	public String salvarEndereco(@PathVariable("idCliente") Integer idCliente, @RequestBody Cartao cartao) {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		cartao.setCliente(cliente);
		String retorno = (String) salvarCartaoCommand.executar(cartao);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Cartão adicionado com sucesso.";
	}

	@PutMapping(path = "/cliente/{idCliente}")
	@ResponseBody
	public String alterarCartao(@PathVariable("idCliente") Integer idCliente, @RequestBody Cartao cartao) {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		cartao.setCliente(cliente);
		String retorno = (String) alterarCartaoCommand.executar(cartao);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Cartao alterado com sucesso.";
	}

	@PutMapping(path = "/cliente/{idCliente}/cartao/{idCartao}")
	@ResponseBody
	public String inativarEnderco(@PathVariable("idCliente") Integer idCliente, @PathVariable("idCartao") Integer idCartao) {
		Cartao cartao = new Cartao();
		cartao.setId(idCartao);
		cartao.setCliente(new Cliente());
		cartao.getCliente().setId(idCliente);
		String retorno = (String) inativarCartaoCommand.executar(cartao);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Cartao inativado com sucesso.";
	}
}
