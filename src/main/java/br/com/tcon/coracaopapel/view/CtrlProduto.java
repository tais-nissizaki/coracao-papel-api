package br.com.tcon.coracaopapel.view;

import java.util.ArrayList;
import java.util.List;

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

import br.com.tcon.coracaopapel.controle.pedido.AlterarPedidoCommand;
import br.com.tcon.coracaopapel.controle.produto.AtivarProdutoCommand;
import br.com.tcon.coracaopapel.controle.produto.ConsultarProdutoCommand;
import br.com.tcon.coracaopapel.controle.produto.InativarProdutoCommand;
import br.com.tcon.coracaopapel.controle.produto.SalvarProdutoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Categoria;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@Controller
@RequestMapping("produtos")
public class CtrlProduto {

	@Autowired
	private ConsultarProdutoCommand consultarProdutoCommand;

	@Autowired
	private InativarProdutoCommand inativarProdutoCommand;

	@Autowired
	private AtivarProdutoCommand ativarProdutoCommand;

	@Autowired
	private SalvarProdutoCommand salvarProdutoCommand;

	@Autowired
	private AlterarPedidoCommand alterarProdutoCommand;

	@GetMapping
	@ResponseBody
	public List<EntidadeDominio> obterTodosProdutos() {
		Produto produto = new Produto();
		produto.setAtivo(true);
		return consultarProdutoCommand.executar(produto);
	}

	@GetMapping(path = "/{idProduto}")
	@ResponseBody
	public List<EntidadeDominio> obterProduto(@PathVariable(name = "idProduto") Integer idProduto) {
		Produto produto = new Produto();
		produto.setId(idProduto);
		return consultarProdutoCommand.executar(produto);
	}

	@GetMapping(path = "/filtrar")
	@ResponseBody
	public List<EntidadeDominio> obterProdutos(
			@RequestParam(name = "produto", required = false) String nomeProduto,
			@RequestParam(name = "idCategoria", required = false) Integer idCategoria,
			@RequestParam(name = "autor", required = false) String autor,
			@RequestParam(name = "isbn", required = false) String isbn,
			@RequestParam(name = "editora", required = false) String editora,
			@RequestParam(name = "codigoBarras", required = false) String codigoBarras) {
		Produto produto = new Produto();
		produto.setTitulo(nomeProduto);
		if (idCategoria != null) {
			Categoria categoria = new Categoria();
			categoria.setId(idCategoria);
			ArrayList<Categoria> categoriasProduto = new ArrayList<>();
			categoriasProduto.add(categoria);
			produto.setCategorias(categoriasProduto);
		}
		produto.setAutor(autor);
		produto.setIsbn(isbn);
		produto.setEditora(editora);
		produto.setCodigoBarras(codigoBarras);
		return consultarProdutoCommand.executar(produto);
	}
	

	@GetMapping(path = "/reservado/filtrar")
	@ResponseBody
	public List<EntidadeDominio> obterProdutosReservados(@RequestParam(name = "produto") String nomeProduto) {
		Produto produto = new Produto();
		produto.setTitulo(nomeProduto);
		return consultarProdutoCommand.executar(produto);
	}

	@PutMapping(path = "/{idProduto}/inativar", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public Object inativarProduto(@PathVariable(name = "idProduto") Integer idProduto) {
		Produto produto = new Produto();
		produto.setId(idProduto);
		Object resultado = inativarProdutoCommand.executar(produto);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Produto inativado com sucesso";
	}

	@PutMapping(path = "/{idProduto}/ativar", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public Object ativarProduto(@PathVariable(name = "idProduto") Integer idProduto) {
		Produto produto = new Produto();
		produto.setId(idProduto);
		Object resultado = ativarProdutoCommand.executar(produto);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Produto ativado com sucesso";
	}

	@PostMapping(path = "/cadastrar", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public Object cadastrarProduto(@RequestBody Produto produto) {
		Object resultado = salvarProdutoCommand.executar(produto);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Produto cadastrado com sucesso";
	}

	@PutMapping(path = "/alterar/{idProduto}", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public Object alterarProduto(@PathVariable(name = "idProduto") Integer idProduto, @RequestBody Produto produtoReq) {
		Produto produto = new Produto();
		produto.setId(idProduto);
		List<EntidadeDominio> produtos = consultarProdutoCommand.executar(produto);
		Produto produtoDB = (Produto)produtos.get(0);
		produtoDB.setAnoEdicao(produtoReq.getAnoEdicao());
		produtoDB.setAutor(produtoReq.getAutor());
		produtoDB.setCategorias(produtoReq.getCategorias());
		produtoDB.setCodigoBarras(produtoReq.getCodigoBarras());
		produtoDB.setEdicao(produtoReq.getEdicao());
		produtoDB.setEditora(produtoReq.getEditora());
		produtoDB.setGrupoPrecificacao(produtoReq.getGrupoPrecificacao());
		produtoDB.setImageBase64(produtoReq.getImageBase64());
		produtoDB.setIsbn(produtoReq.getIsbn());
		produtoDB.setNumeroPaginas(produtoReq.getNumeroPaginas());
		produtoDB.setSinopse(produtoReq.getSinopse());
		produtoDB.setTitulo(produtoReq.getTitulo());
		produtoDB.getDimensao().setAltura(produtoReq.getDimensao().getAltura());
		produtoDB.getDimensao().setLargura(produtoReq.getDimensao().getLargura());
		produtoDB.getDimensao().setProfundidade(produtoReq.getDimensao().getProfundidade());
		produtoDB.getDimensao().setPeso(produtoReq.getDimensao().getPeso());
		

//		produtoDB.setValor(null);
		
		
		
		Object resultado = alterarProdutoCommand.executar(produtoDB);
		if(resultado != null && resultado instanceof String && !((String)resultado).isBlank()) {
			return "Erro: " + resultado;
		}
		return "Produto alterado com sucesso";
	}
}
