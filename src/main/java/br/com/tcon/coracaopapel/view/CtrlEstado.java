package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.estado.ConsultarEstadoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;

@Controller
@RequestMapping("estados")
public class CtrlEstado {
	
	@Autowired
	private ConsultarEstadoCommand consultarEstadocCommand;
	
	
	@GetMapping
	@ResponseBody
	public List<Estado> obterTodosEstados() {
		return (List<Estado>) consultarEstadocCommand.executar(new Estado());
	}
	
	@GetMapping("/sigla/{uf}")
	@ResponseBody
	public List<Estado> obterEstadoPorSigla(@PathVariable String uf) {
		Estado estado = new Estado();
		estado.setDescricao(uf);
		return (List<Estado>) consultarEstadocCommand.executar(estado);
	}

	@GetMapping("/id/{id}")
	@ResponseBody
	public List<Estado> obterEstadoPorId(@PathVariable Integer id) {
		Estado estado = new Estado();
		estado.setId(id);
		return (List<Estado>) consultarEstadocCommand.executar(estado);
	}
}
