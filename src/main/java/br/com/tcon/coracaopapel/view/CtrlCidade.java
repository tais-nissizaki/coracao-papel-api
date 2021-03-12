package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.cidade.ConsultarCidadeCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Cidade;
import br.com.tcon.coracaopapel.modelo.dominio.Estado;

@Controller
@RequestMapping("cidades")
public class CtrlCidade {
	
	@Autowired
	private ConsultarCidadeCommand consultarCidadeCommand;
	
	@GetMapping("/uf/{uf}")
	@ResponseBody
	public List<Cidade> obterCidadesPorSiglaUf(@PathVariable String uf) {
		Cidade cidade = new Cidade();
		Estado estado = new Estado();
		estado.setDescricao(uf);
		cidade.setEstado(estado);
		return (List<Cidade>) consultarCidadeCommand.executar(cidade);
	}

	@GetMapping("/id/{id}")
	@ResponseBody
	public List<Cidade> obterCidadePorId(@PathVariable Integer id) {
		Cidade cidade = new Cidade();
		cidade.setId(id);
		return (List<Cidade>) consultarCidadeCommand.executar(cidade);
	}
}
