package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.endereco.ConsultarEnderecoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.TipoEndereco;

@Controller
@RequestMapping("enderecos")
public class CtrlEndereco {
	
	@Autowired
	private ConsultarEnderecoCommand consultarEnderecoCommand;
	
	
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
		return (List<EntidadeDominio>)consultarEnderecoCommand.executar(endereco);
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
}
