package br.com.tcon.coracaopapel.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.produto.ConsultarProdutoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@Controller
@RequestMapping("produtos")
public class CtrlProduto {

	@Autowired
	private ConsultarProdutoCommand consultarProdutoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosProdutos() {
		return consultarProdutoCommand.executar(new Produto());
	}
	

	@GetMapping(path = "filtrar")
	@ResponseBody
	public List<EntidadeDominio> obterProdutos(@RequestParam(name = "produto") String nomeProduto) {
		Produto produto = new Produto();
		produto.setTitulo(nomeProduto);
		return consultarProdutoCommand.executar(produto);
	}
}
