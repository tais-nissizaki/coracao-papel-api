package br.com.tcon.coracaopapel.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.carrinho.AlterarCarrinhoCommand;
import br.com.tcon.coracaopapel.controle.carrinho.ConsultarCarrinhoCommand;
import br.com.tcon.coracaopapel.controle.carrinho.ExcluirCarrinhoCommand;
import br.com.tcon.coracaopapel.controle.carrinho.SalvarCarrinhoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCarrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@Controller
@RequestMapping("carrinho")
public class CtrlCarrinho {
	
	@Autowired
	private ConsultarCarrinhoCommand consultarCarrinhoCommand;
	
	@Autowired
	private SalvarCarrinhoCommand salvarCarrinhoCommand;
	
	@Autowired
	private AlterarCarrinhoCommand alterarCarrinhoCommand;
	
	@Autowired
	private ExcluirCarrinhoCommand excluirCarrinhoCommand;

	@GetMapping(path = {
			"{idCarrinho}",
			"cliente/{idCliente}"
			})
	@ResponseBody
	public Carrinho obterCarrinho( @PathVariable(required = false) Integer idCliente, @PathVariable(required = false) Integer idCarrinho) {

		System.out.println("idCliente: " + idCliente);
		System.out.println("idCarrinho: " + idCarrinho);
		Carrinho carrinho = new Carrinho();
		carrinho.setId(idCarrinho);
		if(idCliente != null) {
			carrinho.setCliente(new Cliente());
			carrinho.getCliente().setId(idCliente);
		}
		List<Carrinho> carrinhos = (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
		if(carrinhos != null && !carrinhos.isEmpty()) {
			return carrinhos.get(0);
		}
		
		return null;
	}
	
	@DeleteMapping(path = "{idCarrinho}")
	@ResponseBody
	public String excluirCarrinho(@PathVariable("idCarrinho") Integer idCarrinho) {
		Carrinho carrinho = new Carrinho();
		carrinho.setId(idCarrinho);
		try {
			excluirCarrinhoCommand.executar(carrinho);
			return "Carrinho excluído com sucesso!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro: Falha ao excluir o carrinho do cliente";
		}
	}

	@PutMapping
	@ResponseBody
	public ResponseEntity<Carrinho> atualizar(@RequestBody Carrinho carrinho) {
		List<Carrinho> carrinhos = (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
		Carrinho carrinhoBD = carrinhos.get(0);
		List<ItemCarrinho> itensAManter = new ArrayList<ItemCarrinho>();
		for(ItemCarrinho itemCarrinhoBD: carrinhoBD.getItensCarrinho()) {
			for(ItemCarrinho itemCarrinho: carrinho.getItensCarrinho()) {
				if(itemCarrinhoBD.getProduto().getId().equals(itemCarrinho.getProduto().getId())) {
					itemCarrinhoBD.setQuantidade(itemCarrinho.getQuantidade());
					itensAManter.add(itemCarrinho);
				}
			}
		}
		carrinhoBD.setItensCarrinho(itensAManter);
		String retorno = (String) alterarCarrinhoCommand.executar(carrinhoBD);
//		List<Carrinho> carrinhos = (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
		int status = 200;
		if (retorno != null && !retorno.isBlank()) {
			status = 423;
		}
		return ResponseEntity.status(status).body(((List<Carrinho>) consultarCarrinhoCommand.executar(carrinho)).get(0));
	}
			
	@PostMapping(path = {
			"",
			"{idCarrinho}",
			"cliente/{idCliente}"
			})
	@ResponseBody
	public ResponseEntity<Carrinho> adicionarAoCarrinho( 
			@PathVariable(required = false) Integer idCliente,
			@PathVariable(required = false) Integer idCarrinho,
			@RequestBody ItemCarrinho itemCarrinho) {
		itemCarrinho.setDtCadastro(new Date());
		Carrinho carrinho = new Carrinho();
		carrinho.setId(idCarrinho);
		if(idCliente != null) {
			carrinho.setCliente(new Cliente());
			carrinho.getCliente().setId(idCliente);
		}
		List<Carrinho> carrinhos = null;
		if(idCliente != null || idCarrinho != null) {
			carrinhos = (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
		}
		int status = 200;
		if(carrinhos != null && !carrinhos.isEmpty()) {
			carrinho = carrinhos.get(0);
			boolean encontrou = false;
			for(int i=0; i<carrinho.getItensCarrinho().size(); i++) {
				if (carrinho.getItensCarrinho().get(i).getProduto().getId().equals(itemCarrinho.getProduto().getId())) {
					encontrou = true;
				}
				if(carrinho.getItensCarrinho().get(i).getCarrinho() == null) {
					carrinho.getItensCarrinho().get(i).setCarrinho(carrinho);
				}
			}
			if(!encontrou) {
				carrinho.getItensCarrinho().add(itemCarrinho);
			}
			itemCarrinho.setCarrinho(carrinho);
			String retorno = (String) alterarCarrinhoCommand.executar(carrinho);
			if (retorno != null && !retorno.isBlank()) {
				status = 423;
			}
			carrinhos = (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
			carrinho = carrinhos.get(0);
		} else {
			carrinho.setDtCadastro(new Date());
			carrinho.setItensCarrinho(new ArrayList<>());
			itemCarrinho.setCarrinho(carrinho);
			carrinho.getItensCarrinho().add(itemCarrinho);
			String retorno = (String) salvarCarrinhoCommand.executar(carrinho);
			if (retorno != null && !retorno.isBlank()) {
				status = 423;
			}
		}
		
		return ResponseEntity.status(status).body(carrinho);
	}
	
	public Carrinho obterDadosCarrinho() {
		return null;
	}
	
	@GetMapping(path = "produto")
	@ResponseBody
	public List<Carrinho> carrinhos(@RequestParam("titulo") String titulo) {
		Carrinho carrinho = new Carrinho();
		ArrayList<ItemCarrinho> itensCarrinho = new ArrayList<>();
		ItemCarrinho itemCarrinho = new ItemCarrinho();
		Produto produto = new Produto();
		produto.setTitulo(titulo);
		itemCarrinho.setProduto(produto);
		itensCarrinho.add(itemCarrinho);
		carrinho.setItensCarrinho(itensCarrinho);
		return (List<Carrinho>) consultarCarrinhoCommand.executar(carrinho);
	}

}
