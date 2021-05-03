package br.com.tcon.coracaopapel.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tcon.coracaopapel.dao.CarrinhoDAO;
import br.com.tcon.coracaopapel.dao.CartaoDAO;
import br.com.tcon.coracaopapel.dao.ClienteDAO;
import br.com.tcon.coracaopapel.dao.EnderecoDAO;
import br.com.tcon.coracaopapel.dao.PedidoDAO;
import br.com.tcon.coracaopapel.dao.ProdutoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Cartao;
import br.com.tcon.coracaopapel.modelo.dominio.Cliente;
import br.com.tcon.coracaopapel.modelo.dominio.Endereco;
import br.com.tcon.coracaopapel.modelo.dominio.Pedido;
import br.com.tcon.coracaopapel.modelo.dominio.PedidoPagamento;
import br.com.tcon.coracaopapel.modelo.dominio.Permissao;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@RestController
@RequestMapping(path = "/teste")
public class Teste {
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private CarrinhoDAO carrinhoDAO;
	
	@Autowired
	private PedidoDAO pedidoDAO;
	
	@Autowired
	private EnderecoDAO enderecoDAO;

	@Autowired
	private CartaoDAO cartaoDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<Map<String, Object>> map() {
		return List.of(
				Map.of("produto", new Produto(), "disponivel", 12),
				Map.of("produto", new Produto(), "disponivel", 11),
				Map.of("produto", new Produto(), "disponivel", 31)
				);
	}
	
	@PostMapping
	public String teste(@RequestBody Cliente cliente) {
		cliente.setDtCadastro(new Date());
		cliente.setAtivo(true);

		cliente.getUsuario().setDtCadastro(new Date());
		
		cliente.getEnderecos().iterator().next().setDtCadastro(new Date());
		
		cliente.getCartoes().get(0).setDtCadastro(new Date());

		cliente.getDocumentos().get(0).setDtCadastro(new Date());
		
		cliente.getTelefones().get(0).setDtCadastro(new Date());

		cliente.getUsuario().setPermissoes(new ArrayList<>());
		Permissao permissao = new Permissao();
		permissao.setId(2);
		cliente.getUsuario().getPermissoes().add(permissao );
		cliente.getUsuario().setSenha(passwordEncoder.encode(cliente.getUsuario().getSenha()));
		System.out.println(cliente);
		clienteDAO.salvar(cliente);
		return "";
	}
	
	@PostMapping(path = "produto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String produto(@RequestPart("produto") Produto produto, @RequestPart("imagem") MultipartFile imagem) {
		try {
			produto.setImagem(imagem.getBytes());
			produtoDAO.salvar(produto);
			return "ok";
		} catch (IOException e) {
			e.printStackTrace();
			return "Falha a obter imagem do produto";
		}
		
	}
	

	@PostMapping(path = "/carrinho")
	public String carrinho(@RequestBody Carrinho carrinho) {
		carrinho.setDtCadastro(new Date());
		if(carrinho.getItensCarrinho() != null) {
			carrinho.getItensCarrinho().forEach(item -> {
				item.setDtCadastro(new Date());
				item.setCarrinho(carrinho);
			});
		}
		carrinhoDAO.salvar(carrinho);
		return "carrinho";
	}
	
	@PostMapping(path = "/pedido")
	public String pedido(@RequestBody Pedido pedido) {
		pedido.setDtCadastro(new Date());
		pedido.getItensPedido().forEach(item -> {
			item.setDtCadastro(new Date());
			item.setPedido(pedido);
		});
		pedido.getEnderecoEntrega().setDtCadastro(new Date());
		pedido.getEnderecoEntrega().setPedido(pedido);
		if (pedido.getEnderecoEntrega().getEndereco().getId() == null) {
			pedido.getEnderecoEntrega().getEndereco().setDtCadastro(new Date());
		} else {
			Endereco orElse = (Endereco)enderecoDAO.consultar(pedido.getEnderecoEntrega().getEndereco())
						.stream()
						.findFirst()
						.orElse(null);
			pedido.getEnderecoEntrega().setEndereco(orElse);
		}
		
		if(pedido.getPedidoPagamentos()!= null) {
			for(PedidoPagamento pedidoPagamento: pedido.getPedidoPagamentos()) {
				pedidoPagamento.setDtCadastro(new Date());
				pedidoPagamento.setPedido(pedido);
			if(pedidoPagamento.getCartao().getId() == null) {
				pedidoPagamento.getCartao().setDtCadastro(new Date());
			} else {
				Cartao orElse = (Cartao)cartaoDAO.consultar(pedidoPagamento.getCartao())
							.stream()
							.findFirst()
							.orElse(null);
				pedidoPagamento.setCartao(orElse);
			}
			}
		}
		
		
		pedidoDAO.salvar(pedido);
		return pedido.getId().toString();
	}

}
