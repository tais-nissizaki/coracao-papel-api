package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.fornecedor.ConsultarFornecedorCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Fornecedor;

@Controller
@RequestMapping("fornecedores")


public class CtrlFornecedor {
	
	@Autowired
	private ConsultarFornecedorCommand consultarFornecedorCommand;

	@GetMapping
	@ResponseBody
	public List<Fornecedor> obterTodosFornecedores() {
		return (List<Fornecedor>) consultarFornecedorCommand.executar(new Fornecedor());
	}
}
