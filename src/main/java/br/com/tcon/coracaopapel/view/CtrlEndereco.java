package br.com.tcon.coracaopapel.view;

import java.util.Iterator;
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

import br.com.tcon.coracaopapel.controle.endereco.AlterarEnderecoCommand;
import br.com.tcon.coracaopapel.controle.endereco.ConsultarEnderecoCommand;
import br.com.tcon.coracaopapel.controle.endereco.InativarEnderecoCommand;
import br.com.tcon.coracaopapel.controle.endereco.SalvarEnderecoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;

@Controller
@RequestMapping("enderecos")
public class CtrlEndereco {
	
	@Autowired
	private ConsultarEnderecoCommand consultarEnderecoCommand;
	
	@Autowired
	private SalvarEnderecoCommand salvarEnderecoCommand;
	
	@Autowired
	private AlterarEnderecoCommand alterarEnderecoCommand;

	@Autowired
	private InativarEnderecoCommand inativarEnderecoCommand;
	
	@PostMapping(path = "/cliente/{idCliente}")
	@ResponseBody
	public String salvarEndereco(@PathVariable("idCliente") Integer idCliente, @RequestBody Endereco endereco) {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		endereco.setCliente(cliente);
		String retorno = (String) salvarEnderecoCommand.executar(endereco);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Endereço adicionado com sucesso.";
	}

	@PutMapping(path = "/cliente/{idCliente}")
	@ResponseBody
	public String alterarEndereco(@PathVariable("idCliente") Integer idCliente, @RequestBody Endereco endereco) {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		endereco.setCliente(cliente);
		String retorno = (String) alterarEnderecoCommand.executar(endereco);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Endereço adicionado com sucesso.";
	}
	
	@GetMapping(path = {
			"cliente/{idCliente}",
	})
	@ResponseBody
	public List<EntidadeDominio> obterEnderecosCliente(
			@PathVariable("idCliente") Integer idCliente
			) {
		Endereco endereco = new Endereco();
		endereco.setCliente(new Cliente());
		endereco.getCliente().setId(idCliente);
		List<EntidadeDominio> enderecos = consultarEnderecoCommand.executar(endereco);
		Iterator<EntidadeDominio> iterator = enderecos.iterator();
		while (iterator.hasNext()) {
			Endereco enderecoBD = (Endereco) iterator.next();
			if (enderecoBD.getAtivo() != null && !enderecoBD.getAtivo()) {
				iterator.remove();
			}
		}
		return enderecos;
	}
	

	@GetMapping(path = {
			"cliente/{idCliente}/tipo-endereco/{idTipoEndereco}"
	})
	@ResponseBody
	public List<EntidadeDominio> obterEnderecosClientePorTipo(
			@PathVariable("idCliente") Integer idCliente,
			@PathVariable(name = "idTipoEndereco", required = false) Integer idTipoEndereco
			) {
		Endereco endereco = new Endereco();
		endereco.setCliente(new Cliente());
		endereco.getCliente().setId(idCliente);
		endereco.setTipoEndereco(new TipoEndereco());
		endereco.getTipoEndereco().setId(idTipoEndereco);
		return (List<EntidadeDominio>)consultarEnderecoCommand.executar(endereco);
	}

	@PutMapping(path = "/cliente/{idCliente}/endereco/{idEndereco}")
	@ResponseBody
	public String inativarEnderco(@PathVariable("idCliente") Integer idCliente, @PathVariable("idEndereco") Integer idEndereco) {
		Endereco endereco = new Endereco();
		endereco.setId(idEndereco);
		endereco.setCliente(new Cliente());
		endereco.getCliente().setId(idCliente);
		String retorno = (String) inativarEnderecoCommand.executar(endereco);
		if (retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}
		return "Endereço inativado com sucesso.";
	}
}
