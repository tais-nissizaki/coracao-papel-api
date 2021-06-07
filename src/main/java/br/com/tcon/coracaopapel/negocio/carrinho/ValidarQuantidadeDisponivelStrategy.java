package br.com.tcon.coracaopapel.negocio.carrinho;

import java.util.ArrayList;
import java.util.List;

import br.com.tcon.coracaopapel.dao.CarrinhoDAO;
import br.com.tcon.coracaopapel.dao.ProdutoDAO;
import br.com.tcon.coracaopapel.modelo.dominio.Carrinho;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCarrinho;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;
import br.com.tcon.coracaopapel.negocio.IStrategy;

public class ValidarQuantidadeDisponivelStrategy implements IStrategy {
	
	private CarrinhoDAO carrinhoDAO;
	private ProdutoDAO produtoDAO;

	public ValidarQuantidadeDisponivelStrategy(CarrinhoDAO carrinhoDAO, ProdutoDAO produtoDAO) {
		super();
		this.carrinhoDAO = carrinhoDAO;
		this.produtoDAO = produtoDAO;
	}


	@Override
	public String processar(EntidadeDominio entidade) {
		Carrinho carrinho = (Carrinho) entidade;
		for (ItemCarrinho itemCarrinho: carrinho.getItensCarrinho()) {
			Carrinho carrinhoValidar = new Carrinho();
			carrinhoValidar.setItensCarrinho(new ArrayList<>());
			carrinhoValidar.getItensCarrinho().add(new ItemCarrinho());
			carrinhoValidar.getItensCarrinho().get(0).setProduto(new Produto());
			carrinhoValidar.getItensCarrinho().get(0).getProduto().setId(itemCarrinho.getProduto().getId());
			List<EntidadeDominio> carrinhosComProduto = carrinhoDAO.consultar(carrinhoValidar);
			
			List<EntidadeDominio> produto = produtoDAO.consultar(itemCarrinho.getProduto());
			int quantidadeDisponivel = ((Produto)produto.get(0)).getQuantidadeEstoque() - computarQuantidadeBloqueada(carrinhosComProduto, carrinho, itemCarrinho);
			if(quantidadeDisponivel - itemCarrinho.getQuantidade() < 0) {
				return "O livro " + ((Produto)produto.get(0)).getTitulo() + " não possui quantidade suficiente em estoque.";
			}
		}
		return null;
	}
	
	private int computarQuantidadeBloqueada(List<EntidadeDominio> carrinhos, Carrinho carrinhoAtual, ItemCarrinho itemCarrinho) {
		int quantidadeBloqueada = 0;
		for(EntidadeDominio entidade: carrinhos) {
			Carrinho carrinho = (Carrinho) entidade;
			if(carrinho.getId().equals(carrinhoAtual.getId())) {
				continue;
			}
			for (ItemCarrinho itemCarrinhoBD: carrinho.getItensCarrinho()) {
				if(itemCarrinhoBD.getProduto().getId().equals(itemCarrinho.getProduto().getId())) {
					quantidadeBloqueada += itemCarrinhoBD.getQuantidade();
				}
			}
		}
		return quantidadeBloqueada;
	}

}
