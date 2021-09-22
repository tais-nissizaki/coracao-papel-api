package br.com.tcon.coracaopapel.view;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.cidade.ConsultarCidadeCommand;
import br.com.tcon.coracaopapel.controle.cliente.AlterarClienteCommand;
import br.com.tcon.coracaopapel.controle.cliente.AtivarClienteCommand;
import br.com.tcon.coracaopapel.controle.cliente.InativarClienteCommand;
import br.com.tcon.coracaopapel.controle.cliente.SalvarClienteCommand;
import br.com.tcon.coracaopapel.controle.usuario.AlterarUsuarioCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Documento;
import br.com.tcon.coracaopapel.modelo.dominio.TipoDocumento;
import br.com.tcon.coracaopapel.modelo.dominio.Usuario;

@Controller
@RequestMapping("clientes")
public class CtrlCliente {
	
	@Autowired
	private SalvarClienteCommand salvarClienteCommand;
	
	@Autowired
	private ConsultarCidadeCommand consultarClienteCommand;
	
	@Autowired
	private InativarClienteCommand inativarClienteCommand;
	
	@Autowired
	private AtivarClienteCommand ativarClienteCommand;
	
	@Autowired
	private AlterarClienteCommand alterarClienteCommand;
	
	@Autowired
	private AlterarUsuarioCommand alterarUsuarioCommand;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public Object salvar(@RequestBody Cliente cliente) {
		System.out.println(cliente.getNome());
		Object resultado = salvarClienteCommand.executar(cliente);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Cadastro efetuado com sucesso";
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody()
	public Object alterar(@PathVariable Integer id, @RequestBody Cliente cliente) {
		System.out.println(cliente.getNome());
		cliente.setId(id);
		Object resultado = alterarClienteCommand.executar(cliente);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Alteração efetuada com sucesso";
	}
	
	@GetMapping(path = "/pesquisar/nome/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pesquisarPorNome(@PathVariable String nome) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		return consultarClienteCommand.executar(cliente);
	}
	
	@GetMapping(path = "/pesquisar", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pesquisar(@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "cpf", required = false) String cpf) {
		Cliente cliente = new Cliente();
		cliente.setNome(nome);
		if(cpf != null && !cpf.isBlank()) {
			cliente.setDocumentos(new ArrayList<>());
			Documento documento = new Documento();
			documento.setCodigo(cpf);
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(2);
			documento.setTipoDocumento(tipoDocumento);
			cliente.getDocumentos().add(documento);
		}
		return consultarClienteCommand.executar(cliente);
	}
	
	@GetMapping(path = "/pesquisar/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object pesquisarPorId(@PathVariable Integer id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		return consultarClienteCommand.executar(cliente);
	}
	
	@PutMapping(path = "/inativar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object inativar(@PathVariable Integer id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		Object resultado = inativarClienteCommand.executar(cliente);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Cliente inativado com sucesso";
	}

	
	@PutMapping(path = "/ativar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object ativar(@PathVariable Integer id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		Object resultado = ativarClienteCommand.executar(cliente);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Cliente ativado com sucesso";
	}
	
	@PutMapping(path = "/cliente/{idCliente}/alterar-senha", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String alterarSenha(@PathVariable Integer idCliente, @RequestBody Usuario usuario) {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		usuario.setCliente(cliente);
		String retorno = (String) alterarUsuarioCommand.executar(usuario);
		if(retorno != null && !retorno.isEmpty()) {
			return "Erro: " + retorno;
		} else {
			return "Sucesso";
		}
	}
	
}
