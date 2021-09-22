package br.com.tcon.coracaopapel.view;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tcon.coracaopapel.controle.compra.SalvarCompraCommand;
import br.com.tcon.coracaopapel.controle.produto.AlterarProdutoCommand;
import br.com.tcon.coracaopapel.controle.produto.AtivarProdutoCommand;
import br.com.tcon.coracaopapel.controle.produto.ConsultarProdutoCommand;
import br.com.tcon.coracaopapel.modelo.dominio.Compra;
import br.com.tcon.coracaopapel.modelo.dominio.EntidadeDominio;
import br.com.tcon.coracaopapel.modelo.dominio.ItemCompra;
import br.com.tcon.coracaopapel.modelo.dominio.Produto;

@Controller
@RequestMapping("compras")
public class CtrlCompra {
	
	@Autowired
	private SalvarCompraCommand salvarCompraCommand;
	
	@Autowired
	private ConsultarProdutoCommand consultarProdutoCommand;
	
	@Autowired
	private AlterarProdutoCommand alterarProdutoCommand;
	
	@Autowired
	private AtivarProdutoCommand ativarProdutoCommand;
	
	@PostMapping
	@ResponseBody
	public String efetivarCompra(@RequestBody Compra compra) {
		
		String retorno = (String) salvarCompraCommand.executar(compra);
		if(retorno != null && !retorno.isBlank()) {
			return "Erro: " + retorno;
		}

		Map<Integer, BigDecimal> novoPrecoVenda  = new HashMap<>();
		
		for (ItemCompra itemCompra: compra.getItensCompra()) {
			List<EntidadeDominio> produtosBD = (List<EntidadeDominio>) consultarProdutoCommand.executar(itemCompra.getProduto());
			if(produtosBD != null && !produtosBD.isEmpty()) {
				Produto produto = new Produto();
				Produto produtoBD = (Produto)produtosBD.get(0);
				produto.setId(produtoBD.getId());
				produtoBD.getGrupoPrecificacao().getPercentual();
				produtoBD.setQuantidadeEstoque(produtoBD.getQuantidadeEstoque() + itemCompra.getQuantidade());
				
				BigDecimal novoPreco = obterNovoPrecoVenda(novoPrecoVenda, itemCompra, produto, produtoBD);
				produtoBD.setValor(novoPreco);
				novoPrecoVenda.put(produto.getId(), novoPreco);
				
				String executar = (String) alterarProdutoCommand.executar(produtoBD);
				if (executar != null && !executar.isBlank()) {					
					return "Erro: " + executar;
				}
				String retornoAtivacao = (String) ativarProdutoCommand.executar(produto);
				if (retornoAtivacao != null && !retornoAtivacao.isBlank()) {					
					return "Erro: " + retornoAtivacao;
				}
			}
		}
		return null;
	}

	public BigDecimal obterNovoPrecoVenda(Map<Integer, BigDecimal> novoPrecoVenda, ItemCompra itemCompra,
			Produto produto, Produto produtoBD) {
		BigDecimal percentual = produtoBD.getGrupoPrecificacao().getPercentual();
		BigDecimal percentualComoDecimal = percentual.divide(new BigDecimal(100));
		BigDecimal percentualComoDecimalInvertido = BigDecimal.ONE.add(percentualComoDecimal);
		BigDecimal novoPreco = itemCompra.getValorCompra().multiply(percentualComoDecimalInvertido);
		if(novoPrecoVenda.containsKey(produto.getId())) {
			if(novoPrecoVenda.get(produto.getId()).compareTo(novoPreco) > 0) {
				novoPreco = novoPrecoVenda.get(produto.getId());
			}
		} else { // Esse trecho comentado é para verificar se o já cadastrado é maior que o novo calculado
			if(produtoBD.getValor().compareTo(novoPreco) > 0) {
				novoPreco = produtoBD.getValor();
			}
		}
		return novoPreco;
	}

}
